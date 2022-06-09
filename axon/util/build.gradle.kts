import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.7.0"
    kotlin("kapt") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
}

group = "pl.poznan.put"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

ext {
    set("axonVersion", "4.5.12")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-integration")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.axonframework:axon-spring-boot-starter")
    implementation("org.axonframework.extensions.reactor:axon-reactor-spring-boot-starter")
    implementation("org.axonframework.extensions.kotlin:axon-kotlin")
    implementation("org.axonframework.extensions.tracing:axon-tracing-spring-boot-starter")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.6")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.9")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.axonframework:axon-test")
}

dependencyManagement {
    imports {
        mavenBom("org.axonframework:axon-bom:${extra["axonVersion"]}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
