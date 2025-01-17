plugins {
    kotlin("jvm") version "1.9.22"
    id("com.undefinedcreations.mapper") version "1.1.1"
}

repositories {
    mavenLocal()
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.21.4-R0.1-SNAPSHOT:remapped-mojang")
    compileOnly(project(":common"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileJava {
        options.release.set(8)
    }
    remap {
        minecraftVersion("1.21.4")
    }
}

java {
    disableAutoTargetJvm()
}

kotlin {
    jvmToolchain(21)
}