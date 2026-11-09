package com.pmdm.ud3.temas.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.temas.ui.theme.MiAppTheme
import com.pmdm.ud3.temas.ui.theme.SourceCodePro

/** Estilo de la escala tipográfica + si lo hemos personalizado en Type.kt. */
private data class Estilo(val nombre: String, val personalizado: Boolean, val obtener: (Typography) -> TextStyle)

private val escala = listOf(
    Estilo("displayLarge", true) { it.displayLarge },
    Estilo("headlineMedium", true) { it.headlineMedium },
    Estilo("headlineSmall", false) { it.headlineSmall },
    Estilo("titleLarge", true) { it.titleLarge },
    Estilo("titleMedium", true) { it.titleMedium },
    Estilo("titleSmall", false) { it.titleSmall },
    Estilo("bodyLarge", true) { it.bodyLarge },
    Estilo("bodyMedium", true) { it.bodyMedium },
    Estilo("bodySmall", false) { it.bodySmall },
    Estilo("labelLarge", true) { it.labelLarge },
    Estilo("labelMedium", false) { it.labelMedium }
)

/**
 * Apartado 5.3 — Tipografía personalizada.
 *
 * ★ = estilo redefinido en MiTipografia (Montserrat).
 * Los demás conservan la fuente por defecto de Material 3: ¡compara las formas de las letras!
 */
@Composable
fun PantallaTipografia() {
    val tipografia = MaterialTheme.typography

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(escala) { estilo ->
            val textStyle = estilo.obtener(tipografia)
            Column {
                Text(
                    text = "${if (estilo.personalizado) "★ " else ""}${estilo.nombre} · ${textStyle.fontSize}",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (estilo.personalizado) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                )
                // Usar el estilo del tema (RECOMENDADO)
                Text("Película 2026", style = textStyle)
            }
        }

        item { HorizontalDivider() }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Personalizar un estilo existente con copy()", style = MaterialTheme.typography.labelLarge)
                    Text(
                        "Título en rojo",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.error
                        )
                    )
                    Text("Otra familia: SourceCodePro (monoespaciada)", style = MaterialTheme.typography.labelLarge)
                    Text(
                        "val x = 42 // código",
                        style = MaterialTheme.typography.bodyLarge.copy(fontFamily = SourceCodePro)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun PantallaTipografiaPreview() {
    MiAppTheme(coloresDinamicos = false) {
        PantallaTipografia()
    }
}
