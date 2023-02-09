plugins {
    id("lanbroadcaster.base.java")
}

dependencies {
    compileOnly(projects.lanbroadcasterCommon)
    compileOnly("org.apache.logging.log4j:log4j-core:2.19.0")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))
