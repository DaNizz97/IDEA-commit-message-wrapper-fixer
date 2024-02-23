plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.16.1"
    id("idea")
}

group = "xyz.danizz"
version = "1.1"

repositories {
    mavenCentral()
}

idea {
    module {
        isDownloadSources = true
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.1.5")
    type.set("IC") // Target IDE Platform
    updateSinceUntilBuild = false
    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    signPlugin {
        certificateChainFile.set(file(System.getenv("CERTIFICATE_CHAIN")))
        privateKeyFile.set(file(System.getenv("PRIVATE_KEY")))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
