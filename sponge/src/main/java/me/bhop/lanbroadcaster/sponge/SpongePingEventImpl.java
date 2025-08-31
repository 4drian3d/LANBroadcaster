package me.bhop.lanbroadcaster.sponge;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.MinecraftVersion;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.server.ClientPingServerEvent;
import org.spongepowered.api.network.status.Favicon;
import org.spongepowered.api.network.status.StatusClient;

import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicReference;

public class SpongePingEventImpl implements ClientPingServerEvent {
    private final LANStatusClient statusClient;
    private final LANResponse lanResponse;

    public SpongePingEventImpl(Server server) {
        this.statusClient = new LANStatusClient(
                server.boundAddress().orElse(InetSocketAddress.createUnresolved("localhost", 25565)),
                server.game().platform().minecraftVersion()
        );
        this.lanResponse = new LANResponse(server.motd());
    }
    @Override
    public StatusClient client() {
        return statusClient;
    }

    @Override
    public Response response() {
        return lanResponse;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }

    @Override
    public Cause cause() {
        return Cause.builder().append(statusClient).build();
    }

    private record LANStatusClient(InetSocketAddress address, MinecraftVersion version) implements StatusClient {
        @Override
        public Optional<InetSocketAddress> virtualHost() {
            return Optional.empty();
        }
    }

    private static class LANResponse implements Response {
        private final AtomicReference<Component> description;

        public LANResponse(Component initialDescription) {
            this.description = new AtomicReference<>(initialDescription);
        }

        @Override
        public void setDescription(Component description) {
            this.description.set(description);
        }

        @Override
        public Component description() {
            return description.get();
        }

        @Override
        public Optional<Players> players() {
            return Optional.empty();
        }

        @Override
        public void setHidePlayers(boolean hide) {

        }

        @Override
        public Version version() {
            return new Version() {
                @Override
                public void setName(String name) {
                }

                @Override
                public void setProtocolVersion(int protocolVersion) {
                }

                @Override
                public String name() {
                    return "";
                }

                @Override
                public int protocolVersion() {
                    return 0;
                }

                @Override
                public boolean isLegacy() {
                    return false;
                }

                @Override
                public OptionalInt dataVersion() {
                    return OptionalInt.empty();
                }
            };
        }

        @Override
        public Optional<Favicon> favicon() {
            return Optional.empty();
        }

        @Override
        public void setFavicon(@Nullable Favicon favicon) {
        }
    }
}
