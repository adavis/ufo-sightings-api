import io.kotless.DSLType
import io.kotless.plugin.gradle.dsl.kotless
import io.kotless.plugin.gradle.dsl.Webapp.Route53
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = "info.adavis"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.61" apply true
    id("io.kotless") version "0.1.2" apply true
}

tasks.withType<KotlinJvmCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.3"
        apiVersion = "1.3"
    }
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/squash")
}

val ktorVersion = "1.3.0-rc"

dependencies {
    // Ktor Server
    implementation("io.ktor", "ktor-server-core", ktorVersion)
    implementation("io.ktor", "ktor-server-netty", ktorVersion)
    implementation("io.ktor", "ktor-gson", ktorVersion)

    // Kotless
    implementation("io.kotless", "ktor-lang", "0.1.2")

    // Logging
    implementation("ch.qos.logback", "logback-classic", "1.2.3")

    // GraphQL
    implementation("com.apurebase", "kgraphql", "0.9.1")

    // Dependency Injection
    implementation("org.koin", "koin-ktor", "2.0.0")

    // Database
    implementation("org.jetbrains.squash", "squash-h2", "0.2.2")
}

kotless {
    config {
        bucket = "ktor-kotless-ufo.s3.adavis.info"

        dsl {
            type = DSLType.Ktor
        }

        terraform {
            profile = "default"
            region = "us-east-1"
        }
    }

    webapp {
        route53 = Route53("api", "ktor-kotless-ufo.info")
    }
}