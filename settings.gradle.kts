rootProject.name = "Quasar"
include(
    "server",
    "api",
    "common",
    "v1_21_4"
)

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}
