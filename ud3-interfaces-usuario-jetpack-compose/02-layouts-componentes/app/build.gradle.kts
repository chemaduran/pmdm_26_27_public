plugins {
    alias(libs.plugins.android.application)
    // Plugin del compilador de Compose: transforma las funciones @Composable
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.pmdm.ud3.layouts"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.pmdm.ud3.layouts"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        // Activa Jetpack Compose en este módulo
        compose = true
    }
}

dependencies {
    // --- Base de cualquier app Compose ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)          // ComponentActivity + setContent { }
    implementation(platform(libs.androidx.compose.bom))     // BOM: fija versiones coherentes de Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview) // @Preview
    implementation(libs.androidx.compose.material3)          // Componentes Material 3
    implementation(libs.androidx.compose.material.icons.extended) // Icons.Default.*

    // Herramientas de Preview / Layout Inspector (solo en debug)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
