package me.bhop.lanbroadcaster.paper;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import me.bhop.lanbroadcaster.common.MOTDProvider;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;

import java.util.concurrent.CompletableFuture;

public final class PaperMOTDProvider implements MOTDProvider {
    @Override
    public CompletableFuture<String> provideMOTD() {
        final PaperServerListPingEvent pingEvent = new PaperServerListPingEvent(
            new LANStatusClient(),
                Bukkit.getServer().motd(),
                1,
                Bukkit.getServer().getMaxPlayers(),
                Bukkit.getServer().getVersion(),
                727,
                null
        );
        return CompletableFuture.supplyAsync(() -> {
            pingEvent.callEvent();
            return LegacyComponentSerializer.legacySection().serialize(pingEvent.motd());
        });
    }
}
