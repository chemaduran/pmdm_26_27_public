package com.pmdm.ud3.temas.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pmdm.ud3.temas.R

/*
 * Apartado 5.3 — Tipografía personalizada (ui/theme/Type.kt).
 *
 * Los ficheros .ttf están en app/src/main/res/font/ (míralos en Android Studio).
 * Android genera una constante R.font.<nombre_del_fichero> por cada uno.
 * ⚠️ Los nombres de recurso solo admiten minúsculas, números y "_":
 *    Montserrat-Regular.ttf  ✗     montserrat_regular.ttf  ✓
 *
 * Fuentes con licencia SIL Open Font License (libres para usar en apps):
 *  - Montserrat:      https://github.com/JulietaUla/Montserrat
 *  - Source Code Pro: https://github.com/adobe-fonts/source-code-pro
 */

val Montserrat = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

val SourceCodePro = FontFamily(
    Font(R.font.source_code_pro_regular, FontWeight.Normal)
)

/**
 * Solo redefinimos algunos estilos. Los que no aparecen aquí (headlineSmall,
 * titleSmall, bodySmall, labelMedium...) se quedan con los valores por defecto
 * de Material 3. En la pantalla "Tipografía" verás la diferencia.
 */
val MiTipografia = Typography(
    // Títulos grandes
    displayLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp
    ),

    // Título de pantalla
    headlineMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),

    // Título de sección
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),

    // Título de card/item
    titleMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),

    // Texto principal
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Texto secundario
    bodyMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),

    // Etiquetas de botones, chips
    labelLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)
