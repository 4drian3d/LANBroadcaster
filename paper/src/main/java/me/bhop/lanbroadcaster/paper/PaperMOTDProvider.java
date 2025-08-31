package me.bhop.lanbroadcaster.paper;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import me.bhop.lanbroadcaster.common.MOTDProvider;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Server;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public record PaperMOTDProvider(Server server) implements MOTDProvider {
    @Override
    public CompletableFuture<String> provideMOTD(final ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            final PaperServerListPingEvent pingEvent = new PaperServerListPingEvent(
                    new LANStatusClient(),
                    server.motd(),
                    1,
                    server.getMaxPlayers(),
                    server.getVersion(),
                    727,
                    null
            );
            return LegacyComponentSerializer.legacySection().serialize(
                    pingEvent.callEvent() ? pingEvent.motd() : server.motd());
        }, executor);
    }
}
