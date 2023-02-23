plugins {
    id("lanbroadcaster.shadow")
    alias(libs.plugins.runpaper)
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)
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

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))
