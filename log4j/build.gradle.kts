plugins {
    id("lanbroadcaster.base.java")
}

dependencies {
    compileOnly(projects.lanbroadcasterCommon)
    compileOnly(libs.log4j)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
