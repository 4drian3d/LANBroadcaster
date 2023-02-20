package me.bhop.lanbroadcaster.common;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.function.Supplier;

import me.bhop.lanbroadcaster.common.logger.AbstractLogger;
import static me.bhop.lanbroadcaster.common.Constants.*;

public class LANBroadcaster implements Runnable {
    private final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(2);
    private int failCount = 0;
    private final DatagramSocket socket = createSocket();
    private final int port;
    private final Supplier<String> motd;
    private final AbstractLogger logger;
    private boolean running = true;
    private ScheduledFuture<?> future;

    public LANBroadcaster(
            int port,
            Supplier<String> motd,
            AbstractLogger logger
    ) {
        this.port = port;
        this.motd = motd;
        this.logger = logger;
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
            e.printStackTrace();
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
        String ad = Integer.toString(port);
        logger.info("Broadcasting server with port "+ad+" over LAN.");

        String str = "[MOTD]" + motd.get() + "[/MOTD][AD]" + ad + "[/AD]";
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public void shutdown() {
        this.running = false;
        try {
            EXECUTOR.shutdown();
            EXECUTOR.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            socket.close();
        }
    }
}
