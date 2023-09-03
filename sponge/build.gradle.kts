import org.spongepowered.gradle.plugin.config.PluginLoaders

plugins {
    id("lanbroadcaster.shadow")
    alias(libs.plugins.sponge.gradle)
}

repositories {
    maven("https://repo.spongepowered.org/repository/maven-public/")
}

dependencies {
    implementation(projects.lanbroadcasterLog4j)
}

sponge {
    apiVersion(libs.versions.sponge.api.get())
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
            description("Current Developer")
        }
        contributor("bhop_") {
            description("Fork Developer")
        }
        contributor("Ruan") {
            description("Original Developer")
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))
