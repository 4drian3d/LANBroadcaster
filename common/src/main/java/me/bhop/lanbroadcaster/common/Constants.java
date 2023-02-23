package me.bhop.lanbroadcaster.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class Constants {
    private Constants() {}
    
    public static final String VERSION = "{version}";
    public static final String DESCRIPTION = "{description}";

    static final InetAddress BROADCAST_ADDRESS;
    static final int BROADCAST_PORT = 4445;

    static {
        try {
            BROADCAST_ADDRESS = InetAddress.getByName("224.0.2.60");
        } catch (UnknownHostException e) {
            throw new Error(e);
        }
    }
}