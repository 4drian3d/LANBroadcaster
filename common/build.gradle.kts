plugins {
    id("lanbroadcaster.base.java")
    alias(libs.plugins.blossom)
}

blossom {
    replaceTokenIn("src/main/java/me/bhop/lanbroadcaster/common/Constants.java")
    replaceToken("{version}", version)
    replaceToken("{description}", project.description)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))