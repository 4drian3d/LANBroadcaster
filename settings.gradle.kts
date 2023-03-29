enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "lanbroadcaster-parent"

arrayOf(
    "common",
    "slf4j",
    "log4j",
    "bukkit",
    "paper",
    "velocity",
    "sponge",
    "bungee",
    "krypton"
).forEach {
    include("lanbroadcaster-$it")
    project(":lanbroadcaster-$it").projectDir = file(it)
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
