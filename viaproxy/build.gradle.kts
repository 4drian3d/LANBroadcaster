plugins {
    id("lanbroadcaster.shadow")
}

repositories {
    mavenCentral()
    maven("https://repo.viaversion.com")
}

dependencies {
    implementation(projects.lanbroadcasterLog4j)
    compileOnly("net.raphimc:ViaProxy:3.4.4")
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
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))