import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
}

group = "pl.poznan.put"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

ext {
    set("axonVersion", "4.5.13")
}

dependencies {
    implementation("io.projectreactor:reactor-core")
    implementation("org.springframework.data:spring-data-commons")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.axonframework.extensions.reactor:axon-reactor")
    implementation("org.axonframework.extensions.kotlin:axon-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
}

dependencyManagement {
    imports {
        mavenBom("org.axonframework:axon-bom:${property("axonVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}