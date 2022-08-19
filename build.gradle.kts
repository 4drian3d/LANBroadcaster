plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("net.minecrell.plugin-yml.bungee") version "0.5.2"
    id("net.kyori.blossom") version "1.3.1"
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.spongepowered.org/maven")
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
    implementation("net.md-5:bungeecord-api:1.17-R0.1-SNAPSHOT")
    implementation("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
    implementation("org.spongepowered:spongeapi:7.1.0-SNAPSHOT")
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(11)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))

bukkit {
    main = "me.bhop.lanbroadcaster.bukkit.LANBroadcasterBukkit"
    description = "Broadcasts a Minecraft server over LAN."
    name = "LANBroadcaster"
    version = version

    apiVersion = "1.13"
    
    authors = listOf("Ruan", "bhop_")
}

bungee {
    main = "me.bhop.lanbroadcaster.bungee.LANBroadcasterBungee"
    description = "Broadcasts a Minecraft server over LAN."
    name = "LANBroadcaster"
    version = version
    
    author = "Ruan, bhop_"
}

blossom {
    replaceTokenIn("src/main/java/me/bhop/lanbroadcaster/Constants.java")
    replaceToken("{version}", version)
}
