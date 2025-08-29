plugins {
    id("lanbroadcaster.shadow")
    id("xyz.jpenilla.run-velocity")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(projects.lanbroadcasterSlf4j)
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    runVelocity {
        velocityVersion(libs.versions.velocity.get())
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))