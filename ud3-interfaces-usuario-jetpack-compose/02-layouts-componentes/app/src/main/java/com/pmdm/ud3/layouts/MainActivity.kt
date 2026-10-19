package com.pmdm.ud3.layouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pmdm.ud3.layouts.ui.theme.MiAppTheme

/**
 * Misma estructura mínima que en la lección 1.
 * Toda la app está en AppLayouts.kt.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiAppTheme {
                AppLayouts()
            }
        }
    }
}
