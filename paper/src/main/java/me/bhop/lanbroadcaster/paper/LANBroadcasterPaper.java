package me.bhop.lanbroadcaster.paper;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.slf4j.SLF4JLogger;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class LANBroadcasterPaper extends JavaPlugin {
    private LANBroadcaster broadcaster;

    @Override
    public void onEnable() {
        final Server server = getServer();
        this.broadcaster = new LANBroadcaster(
                server.getPort(),
                () -> LegacyComponentSerializer.legacySection().serialize(server.motd()),
                new SLF4JLogger(getSLF4JLogger()));
        this.broadcaster.schedule();
    }

    @Override
    public void onDisable() {
        broadcaster.shutdown();
        broadcaster = null;
    }
}
