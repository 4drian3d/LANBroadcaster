plugins {
    id("lanbroadcaster.shadow")
    alias(libs.plugins.pluginyml.bungee)
    alias(libs.plugins.runwaterfall)
}

bungee {
    main = "me.bhop.lanbroadcaster.bungee.LANBroadcasterBungee"
    description = project.description as String
    name = "LANBroadcaster"
    version = project.version as String
    author = "Ruan, bhop_, 4drian3d"
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(libs.waterfall)
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))