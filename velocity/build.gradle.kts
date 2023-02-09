plugins {
    id("lanbroadcaster.shadow")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(projects.lanbroadcasterSlf4j)
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(11)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))