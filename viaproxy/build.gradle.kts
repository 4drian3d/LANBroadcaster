plugins {
    id("lanbroadcaster.shadow")
}

repositories {
    mavenCentral()
    maven("https://repo.viaversion.com")
}

dependencies {
    implementation(projects.lanbroadcasterLog4j)
    compileOnly(libs.viaproxy)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
    processResources {
        filesMatching("viaproxy.yml") {
            expand("version" to project.version)
        }
    }
    shadowJar {
        archiveFileName.set("LanBroadcasterViaProxy-${project.version}.jar")
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))