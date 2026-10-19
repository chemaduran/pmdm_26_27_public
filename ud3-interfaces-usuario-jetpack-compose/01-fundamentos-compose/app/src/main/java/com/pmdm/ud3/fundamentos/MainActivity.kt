package com.pmdm.ud3.fundamentos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.pmdm.ud3.fundamentos.ui.PantallaDemostracion
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Apartado 1.4 — Estructura mínima de una app Compose.
 *
 * Compara este fichero con el código de los apuntes: es EXACTAMENTE la misma estructura.
 *  - ComponentActivity  → Activity base para Compose.
 *  - setContent { }     → sustituye al setContentView(R.layout.xxx) del View system.
 *  - MiAppTheme { }     → aplica el tema Material (colores, tipografía).
 *  - Surface            → contenedor base con el color de fondo del tema.
 *
 * Fíjate en que NO hay ningún fichero XML de layout en res/layout: toda la UI es Kotlin.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Dibuja la app de borde a borde (detrás de la barra de estado y de navegación).
        enableEdgeToEdge()
        setContent {
            MiAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaDemostracion()
                }
            }
        }
    }
}
