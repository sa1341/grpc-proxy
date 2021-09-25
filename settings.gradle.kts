rootProject.name = "grpc-proxy"
include("grpc-server")
include("grpc-common")

pluginManagement {
    // 플러그인을 resolve 할 때 Gradle 플러그인 포털을 검색하도록 선언.
    repositories {
        gradlePluginPortal() //플러그인 저장소
    }

    val pluginVersions = mapOf(
        "org.jetbrains.kotlin" to "1.3.71",
        "org.jetbrains.kotlin.plugin" to "1.3.71",
        "org.springframework" to "2.3.0.RELEASE",
        "io.spring" to "1.0.9.RELEASE"
    )

    resolutionStrategy {
        eachPlugin {
            if (pluginVersions.containsKey(requested.id.namespace)) {
                useVersion(pluginVersions[requested.id.namespace])
            }
        }
    }
}

