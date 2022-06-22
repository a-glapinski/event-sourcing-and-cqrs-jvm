plugins {
    id("scala")
    id("io.gatling.gradle") version "3.7.6.3"
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}