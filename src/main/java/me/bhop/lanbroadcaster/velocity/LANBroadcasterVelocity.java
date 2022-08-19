package me.bhop.lanbroadcaster.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import me.bhop.lanbroadcaster.Constants;
import me.bhop.lanbroadcaster.LANBroadcaster;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@Plugin(
    id = "lanbroadcaster",
    name = "LANBroadcaster",
    version = Constants.VERSION,
    description = "Broadcasts a Minecraft server over LAN.",
    authors = {"Ruan", "bhop_"}
)
public class LANBroadcasterVelocity {
    private final ProxyServer proxyServer;
    private LANBroadcaster broadcaster;

    @Inject
    public LANBroadcasterVelocity(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent event) {
        this.broadcaster = new LANBroadcaster(
                LANBroadcaster.createSocket(),
                proxyServer.getBoundAddress().getPort(),
                LegacyComponentSerializer.legacySection().serialize(proxyServer.getConfiguration().getMotd()),
                proxyServer.getBoundAddress().getAddress().getHostAddress(),
                java.util.logging.Logger.getLogger("LANBroadcaster"));
        proxyServer.getScheduler().buildTask(this, this.broadcaster).schedule();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        this.broadcaster.shutdown();
        this.broadcaster = null;
    }
}
