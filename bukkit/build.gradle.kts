plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

bukkit {
    main = "me.bhop.lanbroadcaster.bukkit.LANBroadcasterBukkit"
    description = project.description as String
    name = "LANBroadcaster"
    version = project.version as String

    apiVersion = "1.13"
    
    authors = listOf("Ruan", "bhop_", "4drian3d")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly(project(":lanbroadcaster-common"))
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}