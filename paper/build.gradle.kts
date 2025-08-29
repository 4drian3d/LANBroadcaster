plugins {
    id("lanbroadcaster.shadow")
    id("xyz.jpenilla.run-paper")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(projects.lanbroadcasterSlf4j)
    compileOnly(libs.paper)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    runServer {
        minecraftVersion("1.21.8")
    }
    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to project.version)
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
