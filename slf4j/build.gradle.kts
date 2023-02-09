plugins {
    id("lanbroadcaster.base.java")
}

dependencies {
    compileOnly(projects.lanbroadcasterCommon)
    compileOnly("org.slf4j:slf4j-api:2.0.6")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))