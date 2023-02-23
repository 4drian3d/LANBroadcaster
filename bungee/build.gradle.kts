plugins {
    id("lanbroadcaster.shadow")
    alias(libs.plugins.runwaterfall)
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(libs.waterfall)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filesMatching("bungee.yml") {
            expand("version" to project.version)
        }
    }
    runWaterfall {
        waterfallVersion("1.19")
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))