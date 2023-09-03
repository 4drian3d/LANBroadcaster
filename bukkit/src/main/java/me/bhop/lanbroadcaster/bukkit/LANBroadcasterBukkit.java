package me.bhop.lanbroadcaster.bukkit;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.common.logger.JavaLogger;

@SuppressWarnings("unused")
public class LANBroadcasterBukkit extends JavaPlugin {
    private LANBroadcaster broadcaster;

    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        getLogger().warning("This version of LANBroadcaster is deprecated and will be removed in the future. Consider using Paper in conjunction with LANBroadcasterPaper");
        final Server server = getServer();
        this.broadcaster = new LANBroadcaster(
                server.getPort(),
                server::getMotd,
                new JavaLogger(getLogger()));
        this.broadcaster.schedule();
    }

    @Override
    public void onDisable() {
        broadcaster.shutdown();
        broadcaster = null;
    }
}
