import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*;

buildscript {
    dependencies {
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
    }
}

plugins {
    kotlin("plugin.jpa")
    id("com.google.protobuf") version "0.8.13"
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.querydsl:querydsl-jpa")
    api(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    kapt("com.querydsl:querydsl-apt:4.2.1:jpa")
    runtimeOnly ("mysql:mysql-connector-java")
    implementation("io.grpc:grpc-all:1.33.1")
    implementation("io.grpc:grpc-stub:1.33.1")
    api("io.grpc:grpc-kotlin-stub:0.2.1")
    implementation("io.grpc:grpc-protobuf:1.33.1")
    api("com.google.protobuf:protobuf-java-util:3.13.0")
    implementation("io.grpc:protoc-gen-grpc-kotlin:0.1.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
}

protobuf {
    protoc{
        artifact = "com.google.protobuf:protoc:3.10.1"
    }
    generatedFilesBaseDir = "$projectDir/src/main/kotlin/com.example.grpc/generated"
    plugins {
        id("grpc"){
            artifact = "io.grpc:protoc-gen-grpc-java:1.33.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}
