repositories {
    maven("https://repo.kryptonmc.org/releases")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly(project(":lanbroadcaster-common"))
    compileOnly(project(":lanbroadcaster-log4j"))
    compileOnly("org.kryptonmc:krypton-api:0.66.3")
    annotationProcessor("org.kryptonmc:krypton-annotation-processor:499879f4ef")
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(17)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))