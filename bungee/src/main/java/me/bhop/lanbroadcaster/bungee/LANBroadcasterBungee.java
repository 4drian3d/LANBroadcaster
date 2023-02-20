package me.bhop.lanbroadcaster.bungee;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.common.logger.JavaLogger;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.*;

@SuppressWarnings("unused")
public class LANBroadcasterBungee extends Plugin {
    private final List<LANBroadcaster> broadcasters = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        final Collection<?> listeners = getProxy().getConfigurationAdapter()
                .getList("listeners", Collections.emptyList());
        for (final Object obj : listeners) {
            final Map<String, Object> map = (Map<String, Object>) obj;

            final String host = (String) map.get("host");
            final String[] spl = host.split(":", 2);

            final int port = Integer.parseInt(spl[1]);

            final LANBroadcaster broadcaster = new LANBroadcaster(
                    port,
                    () -> ChatColor.translateAlternateColorCodes('&', (String) map.get("motd")),
                    new JavaLogger(getLogger()));
            broadcasters.add(broadcaster);
        }

        broadcasters.forEach(LANBroadcaster::schedule);
    }

    @Override
    public void onDisable() {
        for (LANBroadcaster broadcaster : broadcasters)
            broadcaster.shutdown();
        broadcasters.clear();
    }
}
