import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.noarg") version "1.6.21"
}

group = "pl.poznan.put"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

ext {
	set("axonVersion", "4.5.11")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-integration")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.axonframework:axon-spring-boot-starter")
	implementation("org.axonframework.extensions.kotlin:axon-kotlin")
	implementation("org.axonframework.extensions.tracing:axon-tracing-spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.axonframework:axon-test")
}

dependencyManagement {
	imports {
		mavenBom("org.axonframework:axon-bom:${extra["axonVersion"]}")
	}
}

noArg {
	annotation("org.axonframework.spring.stereotype.Aggregate")
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
