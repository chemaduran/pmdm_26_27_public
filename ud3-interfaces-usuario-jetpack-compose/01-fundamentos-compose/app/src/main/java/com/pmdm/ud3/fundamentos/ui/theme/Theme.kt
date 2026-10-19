package com.pmdm.ud3.fundamentos.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Tema de la app (el "MiAppTheme" que aparece en los apuntes).
 *
 * De momento usamos las paletas por defecto de Material 3.
 * En la lección 5 (Temas y diseño visual) aprenderás a personalizar
 * colores, tipografía y formas.
 */
@Composable
fun MiAppTheme(
    modoOscuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (modoOscuro) darkColorScheme() else lightColorScheme(),
        content = content
    )
}
