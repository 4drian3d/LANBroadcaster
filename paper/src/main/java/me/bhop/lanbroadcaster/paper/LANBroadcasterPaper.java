package me.bhop.lanbroadcaster.paper;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.slf4j.SLF4JLogger;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

@SuppressWarnings("unused")
public final class LANBroadcasterPaper extends JavaPlugin {
    private LANBroadcaster broadcaster;

    @Override
    public void onEnable() {
        final Logger logger = getSLF4JLogger();
        final Server server = getServer();
        try {
            this.broadcaster = LANBroadcaster.initialize(
                    server.getPort(), new PaperMOTDProvider(server), new SLF4JLogger(logger));
            this.broadcaster.schedule();
        } catch (Exception e) {
            getSLF4JLogger().error("LANBroadcaster could not be initialized.", e);
        }

    }

    @Override
    public void onDisable() {
        if (broadcaster != null) {
            broadcaster.shutdown();
            broadcaster = null;
        }
    }
}
