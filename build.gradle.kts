plugins {
    java
    alias(libs.plugins.runpaper) apply false
}

tasks.clean {
    delete("jar")
}
