plugins {
    id("lanbroadcaster.base.java")
    id("net.kyori.blossom") version "1.3.1"
}

blossom {
    replaceTokenIn("src/main/java/me/bhop/lanbroadcaster/common/Constants.java")
    replaceToken("{version}", version)
    replaceToken("{description}", project.description)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))