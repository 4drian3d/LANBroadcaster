package me.bhop.lanbroadcaster.sponge;

import com.google.inject.Inject;
import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.common.logger.AbstractLogger;
import me.bhop.lanbroadcaster.log4j.Log4JLogger;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("lanbroadcaster")
public class LANBroadcasterSponge {
    private LANBroadcaster broadcaster;
    private final AbstractLogger logger;

    @Inject
    public LANBroadcasterSponge(final Logger logger) {
        this.logger = new Log4JLogger(logger);
    }

    @Listener
    public void onPreInit(final StartedEngineEvent<Server> event) {
        final Server server = event.engine();
        server.boundAddress().ifPresent(in -> {
            try {
                this.broadcaster = LANBroadcaster.initialize(
                        in.getPort(),
                        new SpongeMOTDProvider(server),
                        logger);
                this.broadcaster.schedule();
            } catch (Exception e) {
                logger.error("LANBroadcaster could not be initialized.", e);
            }
        });
    }

    @Listener
    public void onStop(StoppingEngineEvent<Server> event) {
        if (this.broadcaster != null) {
            this.broadcaster.shutdown();
            this.broadcaster = null;
        }
    }
}
