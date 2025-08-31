package me.bhop.lanbroadcaster.sponge;

import me.bhop.lanbroadcaster.common.MOTDProvider;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.server.ClientPingServerEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public record SpongeMOTDProvider(Server server) implements MOTDProvider {
    @Override
    public CompletableFuture<String> provideMOTD(final ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            final ClientPingServerEvent pingEvent = new SpongePingEventImpl(server);
            return LegacyComponentSerializer.legacySection().serialize(server.game().eventManager().post(pingEvent)
                    ? pingEvent.response().description()
                    : server.motd());
        }, executor);
    }
}
