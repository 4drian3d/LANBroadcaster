plugins {
    id("lanbroadcaster.shadow")
    id("xyz.jpenilla.run-paper")
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(libs.bukkit)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
    runServer {
        minecraftVersion("1.16.5")
    }
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))
