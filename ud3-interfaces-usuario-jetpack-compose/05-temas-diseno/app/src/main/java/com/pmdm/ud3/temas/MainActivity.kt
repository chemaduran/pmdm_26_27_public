package com.pmdm.ud3.temas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

/**
 * Esta vez NO envolvemos la app en el tema aquí: el tema depende de los ajustes que
 * elige el usuario (modo oscuro, paleta, colores dinámicos), así que lo aplica AppTemas().
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTemas()
        }
    }
}
