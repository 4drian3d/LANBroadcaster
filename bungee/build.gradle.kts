plugins {
    id("lanbroadcaster.shadow")
    id("net.minecrell.plugin-yml.bungee") version "0.5.2"
}

bungee {
    main = "me.bhop.lanbroadcaster.bungee.LANBroadcasterBungee"
    description = project.description as String
    name = "LANBroadcaster"
    version = project.version as String
    author = "Ruan, bhop_, 4drian3d"
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.17-R0.1-SNAPSHOT")
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))