pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://repository.map.naver.com/archive/maven")
    }
}


rootProject.name = "Flow"
include(":app")
