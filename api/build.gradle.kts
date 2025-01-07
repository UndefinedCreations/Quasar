plugins {
    `java-library`
    kotlin("jvm") version "1.9.22"
    id("com.gradleup.shadow") version "8.3.5"
}

val versionVar = version
val groupIdVar = "com.undefined"
val artifactIdVar = "quasar"

group = groupIdVar
version = versionVar

publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = groupIdVar
            artifactId = artifactIdVar
            version = versionVar.toString()

            from(components["java"])
        }
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly(project(":common"))
    compileOnly(project(":v1_21_4"))
}

kotlin {
    jvmToolchain(21)
}