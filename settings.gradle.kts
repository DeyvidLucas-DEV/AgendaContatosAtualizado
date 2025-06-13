pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AgendaContatosAtualizado"
include(":app")

// Adicionar o plugin KSP
plugins {
    id("com.google.devtools.ksp") version "1.9.21-1.0.15" apply false
}
