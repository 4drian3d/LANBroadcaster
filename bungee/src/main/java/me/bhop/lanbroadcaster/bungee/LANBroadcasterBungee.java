package me.bhop.lanbroadcaster.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.common.logger.JavaLogger;

@SuppressWarnings("unused")
public class LANBroadcasterBungee extends Plugin {
    private final List<LANBroadcaster> broadcasters = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        ProxyServer proxy = getProxy();
        Collection<?> listeners = proxy.getConfigurationAdapter().getList("listeners", null);
        for (Object obj : listeners) {
            Map<String, Object> map = (Map<String, Object>) obj;

            String host = (String) map.get("host");
            String[] spl = host.split(":", 2);

            int port = Integer.parseInt(spl[1]);

            LANBroadcaster broadcaster = new LANBroadcaster(
                    port,
                    () -> ChatColor.translateAlternateColorCodes('&', (String) map.get("motd")),
                    new JavaLogger(getLogger()));
            broadcasters.add(broadcaster);
        }
        for (LANBroadcaster broadcaster : broadcasters)
            proxy.getScheduler().runAsync(this, broadcaster);
    }

    @Override
    public void onDisable() {
        for (LANBroadcaster broadcaster : broadcasters)
            broadcaster.shutdown();
        broadcasters.clear();
    }
}
