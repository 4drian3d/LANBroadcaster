plugins {
    id("lanbroadcaster.base.java")
    alias(libs.plugins.idea.ext)
    alias(libs.plugins.blossom)
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
            }
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))