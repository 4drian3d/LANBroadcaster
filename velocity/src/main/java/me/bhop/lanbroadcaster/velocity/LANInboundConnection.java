package me.bhop.lanbroadcaster.velocity;

import com.velocitypowered.api.network.HandshakeIntent;
import com.velocitypowered.api.network.ProtocolState;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.InboundConnection;

import java.net.InetSocketAddress;
import java.util.Optional;

public final class LANInboundConnection implements InboundConnection {
    @Override
    public InetSocketAddress getRemoteAddress() {
        return InetSocketAddress.createUnresolved("localhost", 25565);
    }

    @Override
    public Optional<InetSocketAddress> getVirtualHost() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getRawVirtualHost() {
        return Optional.empty();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return ProtocolVersion.MAXIMUM_VERSION;
    }

    @Override
    public ProtocolState getProtocolState() {
        return ProtocolState.HANDSHAKE;
    }

    @Override
    public HandshakeIntent getHandshakeIntent() {
        return HandshakeIntent.STATUS;
    }
}
