plugins {
    id("lanbroadcaster.shadow")
}

repositories {
    maven("https://repo.kryptonmc.org/releases")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation(projects.lanbroadcasterLog4j)
    compileOnly("org.kryptonmc:krypton-api:499879f4ef")
    annotationProcessor("org.kryptonmc:krypton-annotation-processor:0.66.3")
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(17)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))