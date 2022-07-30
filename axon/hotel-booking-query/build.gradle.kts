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
	set("axonVersion", "4.5.16")
	set("springCloudVersion", "2021.0.3")
}

dependencies {
	implementation(project(":axon:util"))
	implementation(project(":axon:hotel-booking-common"))
	implementation("org.springframework.boot:spring-boot-starter-integration")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.axonframework:axon-spring-boot-starter")
	implementation("org.axonframework.extensions.mongo:axon-mongo")
	implementation("org.axonframework.extensions.reactor:axon-reactor-spring-boot-starter")
	implementation("org.axonframework.extensions.kotlin:axon-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
	implementation("org.springdoc:springdoc-openapi-webflux-core:1.6.9")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.9")
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
	implementation("io.micrometer:micrometer-core")
	implementation("io.micrometer:micrometer-registry-prometheus")
	implementation("org.axonframework:axon-micrometer")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.axonframework:axon-test")
	testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
	imports {
		mavenBom("org.axonframework:axon-bom:${property("axonVersion")}")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
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
