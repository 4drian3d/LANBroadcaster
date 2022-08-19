import org.spongepowered.gradle.plugin.config.PluginLoaders

plugins {
    id("org.spongepowered.gradle.plugin") version "2.0.2"
}

repositories {
    maven("https://repo.spongepowered.org/repository/maven-public/")
}

dependencies {
    compileOnly(project(":lanbroadcaster-common"))
    compileOnly("org.spongepowered:spongeapi:8.1.0-SNAPSHOT")
}

sponge {
    apiVersion("8.0.0")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }

    plugin("lanbroadcaster") {
        displayName("LanBroadcaster")
        version(project.version as String)
        license("MIT")
        description(project.description as String)
        entrypoint("me.bhop.lanbroadcaster.sponge.LANBroadcasterSponge")
        links {
            homepage("https://github.com/4drian3d/LANBroadcaster")
            source("https://github.com/4drian3d/LANBroadcaster")
            issues("https://github.com/4drian3d/LANBroadcaster/issues")
        }
        contributor("4drian3d") {
            description("Actual Developer")
        }
        contributor("bhop_") {
            description("Fork Developer")
        }
        contributor("Ruan") {
            description("Original Developer")
        }
    }
}