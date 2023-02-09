package me.bhop.lanbroadcaster.common;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

import me.bhop.lanbroadcaster.common.logger.AbstractLogger;

public class LANBroadcaster implements Runnable {
    private static final String BROADCAST_HOST = "224.0.2.60:4445";
    private int failcount = 0;
    private final DatagramSocket socket = createSocket();
    private final int port;
    private final Supplier<String> motd;
    private final AbstractLogger logger;
    private boolean running = true;

    public LANBroadcaster(
            int port,
            Supplier<String> motd,
            AbstractLogger logger
    ) {
        this.port = port;
        this.motd = motd;
        this.logger = logger;
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
        try {
            final byte[] ad = getAd();
            final String[] host = BROADCAST_HOST.split(":");
            final DatagramPacket packet = new DatagramPacket(ad, ad.length, InetAddress.getByName(host[0]), Integer.parseInt(host[1]));
            broadcast(socket, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        socket.close();
    }

    private void broadcast(DatagramSocket socket, DatagramPacket packet) {
        try {
            while (running) {
                try {
                    socket.send(packet);
                    failcount = 0;
                } catch (IOException e) {
                    fail(e);
                }
                Thread.sleep(1500);
            }
        } catch (InterruptedException ignored) {
            // no op
        }
    }

    private void fail(Exception e) throws InterruptedException {
        if (failcount++ == 0)
            e.printStackTrace();
        if (failcount < 5)
            logger.warn("Failed to broadcast. Trying again in 10 seconds...");
        else if (failcount == 5)
            logger.error("Broadcasting will not work until the network is fixed. Warnings disabled.");
        Thread.sleep(8500);
    }

    private byte[] getAd() {
        String ad = Integer.toString(port);
        logger.info("Broadcasting server with port "+ad+" over LAN.");

        String str = "[MOTD]" + motd.get() + "[/MOTD][AD]" + ad + "[/AD]";
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public void shutdown() {
        this.running = false;
    }
}
