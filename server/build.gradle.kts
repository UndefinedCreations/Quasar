import com.undefinedcreations.runServer.RamAmount
import com.undefinedcreations.runServer.ServerType

plugins {
    kotlin("jvm") version "1.9.22"
    id("com.undefinedcreations.runServer") version "0.1.6"
    id("com.gradleup.shadow") version "8.3.5"
}

val versionVar = version
val groupIdVar = "com.undefined"
val artifactIdVar = "quasar"

repositories {

    maven {
        name = "undefined-repo"
        url = uri("https://repo.undefinedcreations.com/releases")
    }

}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    implementation("com.undefined:stellar:0.0.37")

    implementation("net.kyori:adventure-api:4.17.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")

    implementation(project(":common"))
    implementation(project(":api"))
    implementation(project(":v1_21_4"))
}

tasks {
    jar {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set("Quasar-$versionVar.jar")
    }

    runServer {
        serverType(ServerType.SPIGOT)
        minecraftVersion("1.21.4")
        allowedRam(4, RamAmount.GIGABYTE)
        acceptMojangEula()
        serverFolderName { "run" }
    }
}

java {
    disableAutoTargetJvm()
}

kotlin {
    jvmToolchain(21)
}