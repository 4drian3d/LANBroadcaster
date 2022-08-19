plugins {
    id("net.minecrell.plugin-yml.bungee") version "0.5.2"
}

bungee {
    main = "me.bhop.lanbroadcaster.bungee.LANBroadcasterBungee"
    description = project.description as String
    name = "LANBroadcaster"
    version = project.version as String
    
    author = "Ruan, bhop_"
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly(project(":lanbroadcaster-common"))
    compileOnly("net.md-5:bungeecord-api:1.17-R0.1-SNAPSHOT")
}