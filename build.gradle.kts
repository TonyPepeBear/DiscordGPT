plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "com.tonyepepe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // Discord
    implementation("net.dv8tion:JDA:5.0.0-beta.5")
    // OpenAI GPT-3
    implementation("com.theokanning.openai-gpt3-java:service:0.11.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}