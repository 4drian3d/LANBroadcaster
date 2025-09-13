package me.bhop.lanbroadcaster.viaproxy;

import net.lenni0451.lambdaevents.EventHandler;
import net.raphimc.viaproxy.plugins.events.ProxyStartEvent;
import net.raphimc.viaproxy.plugins.events.ProxyStopEvent;

public record ViaProxyListener(LANBroadcasterViaProxy lanBroadcaster) {
    @EventHandler
    public void onProxyStart(ProxyStartEvent event) {
        lanBroadcaster.loadLanBroadcaster();
    }

    @EventHandler
    public void onProxyStop(ProxyStopEvent event) {
        lanBroadcaster.disableLanBroadcaster();
    }
}
