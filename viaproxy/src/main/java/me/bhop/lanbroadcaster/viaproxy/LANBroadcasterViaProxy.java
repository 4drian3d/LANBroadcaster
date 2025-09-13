package me.bhop.lanbroadcaster.viaproxy;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.log4j.Log4JLogger;
import net.raphimc.viaproxy.ViaProxy;
import net.raphimc.viaproxy.plugins.ViaProxyPlugin;
import net.raphimc.viaproxy.plugins.events.ProxyStartEvent;
import net.raphimc.viaproxy.plugins.events.ProxyStopEvent;
import net.raphimc.viaproxy.protocoltranslator.viaproxy.ViaProxyConfig;
import org.apache.logging.log4j.LogManager;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public final class LANBroadcasterViaProxy extends ViaProxyPlugin {
    private final Log4JLogger logger = new Log4JLogger(LogManager.getLogger("LANBroadcaster"));
    private LANBroadcaster broadcaster;

    @Override
    public void onEnable() {
        ViaProxy.EVENT_MANAGER.registerRunnable(this::loadLanBroadcaster, 0, ProxyStartEvent.class);
        ViaProxy.EVENT_MANAGER.registerRunnable(this::disableLanBroadcaster, 0, ProxyStopEvent.class);
    }

    private void loadLanBroadcaster() {
        try {
            final ViaProxyConfig proxyConfig = ViaProxy.getConfig();
            if (proxyConfig.getBindAddress() instanceof final InetSocketAddress address) {
                this.broadcaster = LANBroadcaster.initialize(
                        address.getPort(),
                        executor -> CompletableFuture.completedFuture(proxyConfig.getCustomMotd()),
                        logger);
                this.broadcaster.schedule();
            } else {
                throw new UnsupportedOperationException("Unsupported address type");
            }
        } catch (Exception e) {
            logger.error("LANBroadcaster could not be initialized.", e);
        }
    }

    private void disableLanBroadcaster() {
        if (this.broadcaster != null) {
            this.broadcaster.shutdown();
            this.broadcaster = null;
        }
    }
}
