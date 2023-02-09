package me.bhop.lanbroadcaster.krypton;

import org.kryptonmc.api.Server;
import org.kryptonmc.api.event.Listener;
import org.kryptonmc.api.event.server.ServerStartEvent;
import org.kryptonmc.api.plugin.annotation.Plugin;

import com.google.inject.Inject;

import org.apache.logging.log4j.Logger;

import me.bhop.lanbroadcaster.common.Constants;
import me.bhop.lanbroadcaster.common.LANBroadcaster;
import me.bhop.lanbroadcaster.common.logger.AbstractLogger;
import me.bhop.lanbroadcaster.log4j.Log4JLogger;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@Plugin(
    id = "lanbroadcaster",
    name = "LANBroadcaster",
    version = Constants.VERSION,
    description = Constants.DESCRIPTION,
    authors = {"Ruan", "bhop_", "4drian3d"}
)
public class LANBroadcasterKrypton {
    private final Server server;
    private final AbstractLogger logger;

    @Inject
    public LANBroadcasterKrypton(Server server, Logger logger) {
        this.logger = new Log4JLogger(logger);
        this.server = server;
    }

    @Listener
    public void onServerStart(ServerStartEvent event) {
        LANBroadcaster broadcaster = new LANBroadcaster(
                server.address().getPort(),
                () -> LegacyComponentSerializer.legacySection()
                    .serialize(server.motd()),
                logger);
        server.scheduler().run(this, task -> broadcaster.run());
    }
}
