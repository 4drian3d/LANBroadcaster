package me.bhop.lanbroadcaster.bukkit;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.common.logger.JavaLogger;

public class LANBroadcasterBukkit extends JavaPlugin {
    private LANBroadcaster broadcaster;

    @Override
    public void onEnable() {
        Server server = getServer();
        this.broadcaster = new LANBroadcaster(
                LANBroadcaster.createSocket(),
                server.getPort(),
                server::getMotd,
                new JavaLogger(getLogger()));
        server.getScheduler().runTaskAsynchronously(this, broadcaster);
    }

    @Override
    public void onDisable() {
        broadcaster.shutdown();
        broadcaster = null;
    }
}
