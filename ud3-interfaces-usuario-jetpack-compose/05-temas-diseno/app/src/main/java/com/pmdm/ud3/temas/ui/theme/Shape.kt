package com.pmdm.ud3.temas.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/*
 * Apartado 5.4 — Formas personalizadas (ui/theme/Shape.kt).
 *
 * EXPERIMENTO: cambia medium a RoundedCornerShape(0.dp) y ejecuta.
 * Todas las Card de la app pasan a tener esquinas rectas, sin tocar ninguna pantalla.
 */
val MisFormas = Shapes(
    small = RoundedCornerShape(4.dp),     // Chips, small buttons
    medium = RoundedCornerShape(12.dp),   // Cards, dialogs
    large = RoundedCornerShape(16.dp)     // Bottom sheets, large cards
)
