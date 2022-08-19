plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

allprojects {
    apply<JavaPlugin>()

    repositories {
        mavenCentral()
    }

    tasks.compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(11)
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

dependencies {
    shadow(project(":lanbroadcaster-common"))
    shadow(project(":lanbroadcaster-slf4j"))
    shadow(project(":lanbroadcaster-sponge"))
    shadow(project(":lanbroadcaster-velocity"))
    shadow(project(":lanbroadcaster-bukkit"))
    shadow(project(":lanbroadcaster-bungee"))
}

tasks {
    shadowJar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName.set("LanBroadcaster.jar")
        configurations = listOf(project.configurations.shadow.get())
    }

    build {
        dependsOn(shadowJar)
    }
}
