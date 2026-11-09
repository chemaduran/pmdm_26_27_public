package com.pmdm.ud3.temas.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/*
 * Apartado 5.2 — Configuración del tema (ui/theme/Theme.kt).
 *
 *   MaterialTheme
 *   ├── colorScheme   → EsquemaClaro / EsquemaOscuro / dinámico
 *   ├── typography    → MiTipografia   (Type.kt)
 *   ├── shapes        → MisFormas      (Shape.kt)
 *   └── content { }   → toda la app
 */

// ---------------- Paleta "Azul y naranja" ----------------

private val EsquemaClaro = lightColorScheme(
    primary = AzulPrimario,
    onPrimary = Color.White,
    primaryContainer = AzulClaro,
    onPrimaryContainer = AzulOscuro,
    secondary = NaranjaSecundario,
    onSecondary = Color.White,
    secondaryContainer = NaranjaClaro,
    onSecondaryContainer = NaranjaOscuro,
    background = Fondo,
    onBackground = Color(0xFF1C1B1F),
    surface = Superficie,
    onSurface = Color(0xFF1C1B1F),
    error = Error,
    onError = Color.White
)

private val EsquemaOscuro = darkColorScheme(
    primary = AzulClaro,
    onPrimary = AzulOscuro,
    primaryContainer = AzulPrimario,
    onPrimaryContainer = Color.White,
    secondary = NaranjaClaro,
    onSecondary = NaranjaOscuro,
    secondaryContainer = NaranjaSecundario,
    onSecondaryContainer = Color.White,
    background = Color(0xFF121212),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE6E1E5),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

/**
 * Tema principal (apartados 5.2 y 5.5).
 *
 * @param modoOscuro       por defecto sigue al sistema (isSystemInDarkTheme()).
 * @param coloresDinamicos solo funciona en Android 12+ (API 31, Build.VERSION_CODES.S):
 *                         la paleta se genera a partir del fondo de pantalla del usuario.
 */
@Composable
fun MiAppTheme(
    modoOscuro: Boolean = isSystemInDarkTheme(),
    coloresDinamicos: Boolean = true,      // Solo Android 12+
    content: @Composable () -> Unit
) {
    val esquemaColores = when {
        coloresDinamicos && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (modoOscuro) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        modoOscuro -> EsquemaOscuro
        else -> EsquemaClaro
    }

    MaterialTheme(
        colorScheme = esquemaColores,
        typography = MiTipografia,
        shapes = MisFormas,
        content = content
    )
}

// ---------------- Paleta "Naturaleza" (apartado 5.9) ----------------

private val PaletaClara = lightColorScheme(
    primary = VerdeNaturaleza,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC8E6C9),
    onPrimaryContainer = VerdeOscuro,
    secondary = MarronTierra,
    onSecondary = Color.White,
    background = CremaFondo,
    surface = Color.White
)

private val PaletaOscura = darkColorScheme(
    primary = VerdeClaro,
    onPrimary = VerdeOscuro,
    primaryContainer = VerdeNaturaleza,
    onPrimaryContainer = Color.White,
    secondary = MarronClaro,
    onSecondary = MarronOscuro,
    background = Color(0xFF1B1B1B),
    surface = Color(0xFF2D2D2D)
)

/**
 * Segundo tema completo. Fíjate en que los roles que NO definimos en PaletaClara
 * (tertiary, surfaceVariant, error...) toman los valores por defecto de Material 3.
 */
@Composable
fun NaturalezaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) PaletaOscura else PaletaClara,
        typography = MiTipografia,
        shapes = MisFormas,
        content = content
    )
}

/** Las paletas que se pueden elegir en la app. */
enum class Paleta(val nombre: String) {
    AZUL("Azul y naranja"),
    NATURALEZA("Naturaleza")
}
