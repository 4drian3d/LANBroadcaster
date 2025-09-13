plugins {
    java
}

repositories {
    mavenCentral()
}

tasks{
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
}