package me.bhop.lanbroadcaster.velocity;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import me.bhop.lanbroadcaster.common.Constants;
import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.slf4j.SLF4JLogger;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@Plugin(
    id = "lanbroadcaster",
    name = "LANBroadcaster",
    version = Constants.VERSION,
    description = "Broadcasts a Minecraft server over LAN.",
    authors = {"Ruan", "bhop_", "4drian3d"}
)
public class LANBroadcasterVelocity {
    private final ProxyServer proxyServer;
    private final SLF4JLogger logger;
    private LANBroadcaster broadcaster;

    @Inject
    public LANBroadcasterVelocity(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = new SLF4JLogger(logger);
    }

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent event) {
        this.broadcaster = new LANBroadcaster(
                proxyServer.getBoundAddress().getPort(),
                () -> LegacyComponentSerializer.legacySection()
                    .serialize(proxyServer.getConfiguration().getMotd()),
                logger);
        this.broadcaster.schedule();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        this.broadcaster.shutdown();
        this.broadcaster = null;
    }
}
