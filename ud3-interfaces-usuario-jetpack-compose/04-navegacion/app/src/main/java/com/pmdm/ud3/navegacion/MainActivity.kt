package com.pmdm.ud3.navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pmdm.ud3.navegacion.navegacion.PeliculasApp
import com.pmdm.ud3.navegacion.ui.theme.MiAppTheme

/**
 * Apartado 4.2, paso 3 — Configurar en MainActivity.
 * Una sola Activity: las "pantallas" son composables y las gestiona el NavHost.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiAppTheme {
                PeliculasApp()
            }
        }
    }
}
