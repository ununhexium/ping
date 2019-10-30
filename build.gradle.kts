import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm") version "1.3.50"
}

repositories {
  mavenCentral()
}

dependencies{
  testImplementation("org.assertj:assertj-core:3.13.2")

  testImplementation("org.jsoup:jsoup:1.12.1")

  testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
  implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
  jvmTarget = "1.8"
}