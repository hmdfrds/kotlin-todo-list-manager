plugins {
    kotlin("jvm") version "2.1.10"
    application
    kotlin("plugin.serialization") version "2.1.10"
}

group = "org.example.todolist"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
}

application {
    mainClass.set("org.example.todolist.MainKt")
};

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}