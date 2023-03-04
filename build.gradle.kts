import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "com.tonyepepe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.tonypepe.discordgpt.MainKt")
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

val fatJar = task("fatJar", type = Jar::class) {
    archiveBaseName.set("${project.name}-fat")
    manifest {
        attributes["Main-Class"] = "com.tonypepe.discordgpt.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}