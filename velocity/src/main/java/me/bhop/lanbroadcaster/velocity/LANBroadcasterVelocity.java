package me.bhop.lanbroadcaster.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.bhop.lanbroadcaster.common.Constants;
import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.slf4j.SLF4JLogger;
import org.slf4j.Logger;

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
    public LANBroadcasterVelocity(final ProxyServer proxyServer, final Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = new SLF4JLogger(logger);
    }

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent event) {
        try {
            this.broadcaster = LANBroadcaster.initialize(
                    proxyServer.getBoundAddress().getPort(),
                    new VelocityMOTDProvider(proxyServer, proxyServer.getEventManager()),
                    logger);
            this.broadcaster.schedule();
        } catch (Exception e) {
            logger.error("LANBroadcaster could not be initialized.", e);
        }

    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        if (this.broadcaster != null) {
            this.broadcaster.shutdown();
            this.broadcaster = null;
        }
    }
}
