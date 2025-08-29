package me.bhop.lanbroadcaster.sponge;

import me.bhop.lanbroadcaster.common.MOTDProvider;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.server.ClientPingServerEvent;

import java.util.concurrent.CompletableFuture;

public record SpongeMOTDProvider(Server server) implements MOTDProvider {
    @Override
    public CompletableFuture<String> provideMOTD() {
        return CompletableFuture.supplyAsync(() -> {
            final ClientPingServerEvent pingEvent = new SpongePingEventImpl(server);
            server.game().eventManager().post(pingEvent);
            return LegacyComponentSerializer.legacySection().serialize(pingEvent.response().description());
        });
    }
}
