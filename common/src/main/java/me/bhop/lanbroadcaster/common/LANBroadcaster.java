package me.bhop.lanbroadcaster.common;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.function.Supplier;

import me.bhop.lanbroadcaster.common.logger.AbstractLogger;
import static me.bhop.lanbroadcaster.common.Constants.*;

public final class LANBroadcaster implements Runnable {
    private final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            r -> new Thread(r, "LANBroadcasterExecutor")
    );
    private final DatagramSocket socket = createSocket();
    private final String port;
    private final Supplier<String> motdSupplier;
    private final AbstractLogger logger;
    private int failCount = 0;
    private boolean running = true;
    private ScheduledFuture<?> future;

    public LANBroadcaster(
            final int port,
            final Supplier<String> motdSupplier,
            final AbstractLogger logger
    ) {
        this.port = Integer.toString(port);
        this.motdSupplier = motdSupplier;
        this.logger = logger;
        logger.info("Broadcasting server with port "+port+" over LAN.");
    }

    public void schedule() {
        this.future = EXECUTOR.scheduleAtFixedRate(this, 0, 1500, TimeUnit.MILLISECONDS);
    }

    public static DatagramSocket createSocket() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return socket;
    }

    @Override
    public void run() {
        if (!this.running) {
            future.cancel(false);
            return;
        }
        try {
            final byte[] ad = getAd();
            final DatagramPacket packet = new DatagramPacket(ad, ad.length, BROADCAST_ADDRESS, BROADCAST_PORT);
            socket.send(packet);
            failCount = 0;
        } catch (IOException e) {
            fail(e);
        }
    }

    private void fail(Exception e) {
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

    private byte[] getAd() {
        final String str = "[MOTD]" + motdSupplier.get() + "[/MOTD][AD]" + port + "[/AD]";
        return str.getBytes(StandardCharsets.UTF_8);
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
