plugins {
    id("lanbroadcaster.shadow")
    alias(libs.plugins.runpaper)
    alias(libs.plugins.pluginyml.paper)
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)
}

bukkit {
    main = "me.bhop.lanbroadcaster.bukkit.LANBroadcasterBukkit"
    description = project.description as String
    name = "LANBroadcaster"
    version = project.version as String
    apiVersion = "1.13"
    authors = listOf("Ruan", "bhop_", "4drian3d")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
    runServer {
        minecraftVersion("1.16.5")
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))
