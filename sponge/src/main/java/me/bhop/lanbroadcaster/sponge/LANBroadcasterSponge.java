package me.bhop.lanbroadcaster.sponge;

import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.spongepowered.api.scheduler.Task;

import com.google.inject.Inject;

import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.common.logger.AbstractLogger;
import me.bhop.lanbroadcaster.log4j.Log4JLogger;

import java.net.InetSocketAddress;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@Plugin("lanbroadcaster")
public class LANBroadcasterSponge {
    private LANBroadcaster broadcaster;
    private final AbstractLogger logger;
    private ScheduledTask scheduledTask;
    @Inject
    private PluginContainer container;

    @Inject
    public LANBroadcasterSponge(Logger logger) {
        this.logger = new Log4JLogger(logger);
    }

    @Listener
    public void onPreInit(StartedEngineEvent<Server> event) {
        final Server server = event.engine();
        final Optional<InetSocketAddress> optional = server.boundAddress();
        if (!optional.isPresent()) {
            return;
        }

        final InetSocketAddress in = optional.get();

        this.broadcaster = new LANBroadcaster(
                in.getPort(),
                () -> LegacyComponentSerializer.legacySection().serialize(server.motd()),
                logger);
        final Task task = Task.builder()
                .execute(this.broadcaster)
                .plugin(container)
                .build();
        scheduledTask = server.scheduler().submit(task);
    }

    @Listener
    public void onStop(StoppingEngineEvent<Server> event) {
        if (scheduledTask != null) {
            scheduledTask.cancel();
        }
        this.broadcaster.shutdown();
        this.broadcaster = null;
    }
}
