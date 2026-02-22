plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.melances"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.powernukkitx.org/releases") }
    maven { url = uri("https://repo.powernukkitx.org/snapshots") }
    maven { url = uri("https://repo.opencollab.dev/maven-releases/") }
    maven { url = uri("https://repo.opencollab.dev/maven-snapshots/") }
}

dependencies {
    compileOnly("org.powernukkitx:server:2.0.0-SNAPSHOT")
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }
}
