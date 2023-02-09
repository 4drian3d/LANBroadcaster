plugins {
    id("lanbroadcaster.base.java")
}

dependencies {
    compileOnly(projects.lanbroadcasterCommon)
    compileOnly(libs.slf4j)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))