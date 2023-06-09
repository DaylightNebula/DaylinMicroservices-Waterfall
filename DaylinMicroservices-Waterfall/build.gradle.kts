plugins {
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven("https://jitpack.io/")
}

dependencies {
    implementation("com.github.DaylightNebula.DaylinMicroservices:DaylinMicroservices-Core:0.2.1")
    implementation("io.github.waterfallmc:waterfall-api:1.19-R0.1-SNAPSHOT")
}

tasks.getByName<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    destinationDirectory.set(file("../testwaterfall/plugins"))
}

//kotlin {
//    jvmToolchain(17)
//}

task<Exec>("docker-build") {
    dependsOn("shadowJar")
    commandLine("docker", "build", ".", "-t", "daylinmicroservices/waterfall")
    commandLine("docker", "image", "prune", "-f")
}

task<Exec>("docker-run") {
    dependsOn("docker-build")
    commandLine("./run-docker.bat")
}