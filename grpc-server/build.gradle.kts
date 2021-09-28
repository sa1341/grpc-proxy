plugins {
    id("org.asciidoctor.convert") version "1.5.8"
}

dependencies {
    api(project(":grpc-common"))
    implementation("io.grpc:grpc-netty:1.33.1")
    implementation("io.grpc:grpc-netty-shaded:1.37.0")
    // armeria
    implementation("com.linecorp.armeria:armeria:1.7.2")
    implementation("com.linecorp.armeria:armeria-grpc:1.7.2")
    implementation("io.github.lognet:grpc-spring-boot-starter:4.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    //implementation("com.linecorp.armeria:armeria-spring-boot-webflux-starter:0.95.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.10")
}

// true 시에 *-SNAPSHOT.jar에 기본 Manifest 속성이 없습니다  발생
tasks.jar {
    enabled = false
}

tasks.bootJar {
    isEnabled = true

    val asciidoctor by tasks
    dependsOn(asciidoctor)

    from("${tasks.asciidoctor.get().outputDir}/html5") {
        into("static/docs")
    }
}

val file = file("build/generated-snippets")
val snippetsDir = file.absoluteFile

tasks.asciidoctor {
    attributes(
        mapOf(
            "snippets" to snippetsDir
        )
    )
    val test by tasks
    dependsOn(test)
}

// RestDocs를 문서화를 위한 task
tasks.register("buildWithRestDocs") {
    val bootJar by tasks
    dependsOn(bootJar)
}
