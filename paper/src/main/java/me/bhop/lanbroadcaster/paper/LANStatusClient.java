package me.bhop.lanbroadcaster.paper;

import com.destroystokyo.paper.network.StatusClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;

public final class LANStatusClient implements StatusClient {
    @Override
    public @NotNull InetSocketAddress getAddress() {
        return InetSocketAddress.createUnresolved("localhost", 25565);
    }

    @Override
    public int getProtocolVersion() {
        return 772;
    }

    @Override
    public @Nullable InetSocketAddress getVirtualHost() {
        return null;
    }
}
