
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // Esta opción es una buena práctica y obliga a centralizar los repositorios aquí.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        // Repositorio para librerías de AndroidX y Google
        google()
        // Repositorio para la mayoría de librerías open-source
        mavenCentral()

        // Repositorio específico para MapLibre
        maven {
            name = "MapLibre"
            url = uri("https://repo.maplibre.org/artifactory/maplibre-public/")
            // ¡ESTA ES LA PARTE CLAVE!
            // Le dice a Gradle que SOLO busque aquí las dependencias de MapLibre.
            content {
                includeGroupByRegex("org\\.maplibre.*")
            }
        }
    }
}

rootProject.name = "juego_ra"
include(":app")