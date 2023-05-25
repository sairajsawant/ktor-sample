val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val sqldelight_version: String by project
val hikaricp_version: String by project

plugins {
    kotlin("jvm") version "1.8.21"
    id("io.ktor.plugin") version "2.3.0"
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

group = "com.simplifiers"
version = "0.0.1"
application {
    mainClass.set("com.simplifiers.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")

}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.arrow-kt:arrow-core:1.2.0-RC")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("app.cash.sqldelight:jdbc-driver:$sqldelight_version")
    implementation("redis.clients:jedis:2.7.0")
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("org.postgresql:postgresql:42.2.27")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

sqldelight {
    databases {
        create("SqlDelight") {
            packageName.set("com.simplifiers.sqldelight")
            dialect("app.cash.sqldelight:postgresql-dialect:2.0.0-alpha05")
        }
    }
}