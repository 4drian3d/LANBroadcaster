package me.bhop.lanbroadcaster.sponge;

import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.spongepowered.api.scheduler.Task;

import com.google.inject.Inject;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.common.logger.AbstractLogger;
import me.bhop.lanbroadcaster.sponge.logger.Log4JLogger;

import java.net.InetSocketAddress;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@Plugin("lanbroadcaster")
public class LANBroadcasterSponge {
    private LANBroadcaster broadcaster;
    private final AbstractLogger logger;

    @Inject
    public LANBroadcasterSponge(Logger logger) {
        this.logger = new Log4JLogger(logger);
    }

    @Listener
    public void onPreInit(StartedEngineEvent<Server> event) {
        Server server = event.engine();
        Optional<InetSocketAddress> optional = server.boundAddress();
        if (optional.isEmpty()) {
            return;
        }

        InetSocketAddress in = optional.get();

        this.broadcaster = new LANBroadcaster(
                LANBroadcaster.createSocket(),
                in.getPort(),
                () -> LegacyComponentSerializer.legacySection().serialize(server.motd()),
                logger);
        Task.builder()
            .execute(this.broadcaster)
            .plugin(Sponge.pluginManager().fromInstance(this).orElse(null))
            .build();
    }

    @Listener
    public void onStop(StoppingEngineEvent<Server> event) {
        this.broadcaster.shutdown();
        this.broadcaster = null;
    }
}
