package me.bhop.lanbroadcaster.velocity;

import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import me.bhop.lanbroadcaster.common.MOTDProvider;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public record VelocityMOTDProvider(
        ProxyServer proxyServer,
        EventManager eventManager
) implements MOTDProvider {
    @Override
    public CompletableFuture<String> provideMOTD(final ExecutorService executor) {
        final ProxyPingEvent pingEvent = new ProxyPingEvent(
                new LANInboundConnection(),
                ServerPing.builder()
                        .description(proxyServer.getConfiguration().getMotd())
                        .notModCompatible()
                        .nullPlayers()
                        .build()
        );
        return proxyServer.getEventManager()
                .fire(pingEvent)
                .thenApplyAsync(event -> LegacyComponentSerializer.legacySection().serialize(
                            event.getResult().isAllowed()
                                    ? event.getPing().getDescriptionComponent()
                                    : proxyServer.getConfiguration().getMotd())
                , executor);
    }
}
