package me.bhop.lanbroadcaster.common;

import me.bhop.lanbroadcaster.common.logger.AbstractLogger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

import static me.bhop.lanbroadcaster.common.Constants.BROADCAST_ADDRESS;
import static me.bhop.lanbroadcaster.common.Constants.BROADCAST_PORT;

public final class LANBroadcaster implements Runnable {
    private final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            runnable -> Thread.ofVirtual().name("LANBroadcasterExecutor").unstarted(runnable)
    );
    private final DatagramSocket socket;
    private final String port;
    private final MOTDProvider motdProvider;
    private final AbstractLogger logger;
    private int failCount = 0;
    private boolean running = true;
    private ScheduledFuture<?> future;

    private LANBroadcaster(
            final int port,
            final MOTDProvider motdProvider,
            final AbstractLogger logger,
            final DatagramSocket socket
    ) {
        this.port = Integer.toString(port);
        this.motdProvider = motdProvider;
        this.logger = logger;
        this.socket = socket;
    }

    public static LANBroadcaster initialize(
            final int port,
            final MOTDProvider motdProvider,
            final AbstractLogger logger
    ) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(3000);
        final LANBroadcaster broadcaster = new LANBroadcaster(port, motdProvider, logger, socket);
        logger.info("Broadcasting server with port "+port+" over LAN.");
        return broadcaster;
    }

    public void schedule() {
        this.future = EXECUTOR.scheduleAtFixedRate(this, 0, 1500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        if (!this.running) {
            future.cancel(false);
            return;
        }
        getAd().thenAccept(ad -> {
            final DatagramPacket packet = new DatagramPacket(ad, ad.length, BROADCAST_ADDRESS, BROADCAST_PORT);
            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            failCount = 0;
        }).handle((r, e) -> {
            fail(e);
            return null;
        }).join();
    }

    private void fail(Throwable e) {
        if (failCount++ == 0) {
            logger.warn("Failed to broadcast.", e);
        }

        if (failCount < 5) {
            logger.warn("Failed to broadcast. Trying again in 10 seconds...");
        } else if (failCount == 5) {
            logger.error("Broadcasting will not work until the network is fixed. Warnings disabled.");
        }

        if (this.future != null) {
            this.future.cancel(true);
        }

        EXECUTOR.schedule(this::schedule, 8500, TimeUnit.MILLISECONDS);
    }

    private CompletableFuture<byte[]> getAd() {
        return motdProvider.provideMOTD()
                .thenApply(platformMotd -> "[MOTD]" + platformMotd + "[/MOTD][AD]" + port + "[/AD]")
                .thenApply(formatted -> formatted.getBytes(StandardCharsets.UTF_8));
    }

    public void shutdown() {
        this.running = false;
        try {
            EXECUTOR.shutdown();
            //noinspection ResultOfMethodCallIgnored
            EXECUTOR.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            socket.close();
        }
    }
}
