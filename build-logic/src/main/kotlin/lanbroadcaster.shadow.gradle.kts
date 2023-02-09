plugins {
    id("lanbroadcaster.base.java")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation(project(":lanbroadcaster-common"))
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName.set("${project.name.prettified()}-${project.version}.jar")
        archiveClassifier.set("")
    }
}

repositories {
    mavenCentral()
}

fun String.prettified(): String {
    val st = this.substring(15)
    val char = st[0]
    return "LanBroadcaster${char.toUpperCase()}${st.substring(1)}"
}
