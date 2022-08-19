rootProject.name = "lanbroadcaster-parent"

listOf("common", "slf4j", "bukkit", "velocity", "sponge", "bungee").forEach {
    include("lanbroadcaster-$it")
    project(":lanbroadcaster-$it").projectDir = file(it)
}
