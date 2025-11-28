plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


    // Para integrar KSP
    alias(libs.plugins.kotlin.ksp)

    // Para integrar en el proyecto Hilt
    id("com.google.dagger.hilt.android")}

android {
    namespace = "mx.uacj.juego_ra"
    compileSdk = 35

    defaultConfig {
        applicationId = "mx.uacj.juego_ra"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}


dependencies {

    // --- MapLibre ---
    implementation("org.maplibre.gl:android-sdk:11.11.0")

    // --- Hilt & WorkManager ---
    implementation(libs.hilt.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.ui.graphics) // 'hilt.viewmodel' es redundante
    ksp(libs.hilt.android.compilador)
    implementation(libs.androidx.hilt.navigation.compose) // Add this line

    val androidxhiltCompiler = "1.3.0"
    implementation("androidx.hilt:hilt-work:${androidxhiltCompiler}")
    ksp("androidx.hilt:hilt-compiler:${androidxhiltCompiler}")
    // Actualizamos work-runtime-ktx para que sea compatible con las demás librerías
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // --- Google Play Services & ML Kit ---
    implementation(libs.vision.common)
    implementation(libs.play.services.mlkit.barcode.scanning)
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // --- Accompanist ---
    implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")

    // --- CameraX --- (Usando las versiones del catálogo libs)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.compose)

    // --- Jetpack Core, Compose & Navigation ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose.android)

    // --- Kotlinx Serialization ---
    implementation(libs.kotlinx.serialization.json)

    // --- LIFECYCLE (CORREGIDO Y UNIFICADO) ---
    // Unificamos todas las dependencias de lifecycle a una versión ALTA y consistente
    // para resolver todos los conflictos.
    val lifecycleVersion = "2.8.3"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${lifecycleVersion}")

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}