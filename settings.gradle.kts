rootProject.name = "lanbroadcaster-parent"

listOf("common", "slf4j", "log4j", "bukkit", "velocity", "sponge", "bungee", "krypton").forEach {
    include("lanbroadcaster-$it")
    project(":lanbroadcaster-$it").projectDir = file(it)
}
