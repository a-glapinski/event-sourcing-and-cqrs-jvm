plugins {
    scala
    id("io.gatling.gradle") version "3.8.2"
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}