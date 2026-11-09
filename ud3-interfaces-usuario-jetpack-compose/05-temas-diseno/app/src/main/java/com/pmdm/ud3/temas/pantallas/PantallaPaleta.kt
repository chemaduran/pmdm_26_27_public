package com.pmdm.ud3.temas.pantallas

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.temas.ui.theme.MiAppTheme

/** Un rol de color, su par "on…" y para qué se usa (tabla del apartado 5.2). */
private data class RolDeColor(
    val nombre: String,
    val nombreOn: String,
    val uso: String,
    val color: (ColorScheme) -> Color,
    val colorOn: (ColorScheme) -> Color
)

private val roles = listOf(
    RolDeColor("primary", "onPrimary", "Color principal (botones, elementos activos)", { it.primary }, { it.onPrimary }),
    RolDeColor("primaryContainer", "onPrimaryContainer", "Contenedores con color primario (FAB, chips)", { it.primaryContainer }, { it.onPrimaryContainer }),
    RolDeColor("secondary", "onSecondary", "Color secundario (menos prominente)", { it.secondary }, { it.onSecondary }),
    RolDeColor("secondaryContainer", "onSecondaryContainer", "Contenedores secundarios", { it.secondaryContainer }, { it.onSecondaryContainer }),
    RolDeColor("tertiary", "onTertiary", "Tercer color de acento", { it.tertiary }, { it.onTertiary }),
    RolDeColor("background", "onBackground", "Fondo general de la app", { it.background }, { it.onBackground }),
    RolDeColor("surface", "onSurface", "Superficies (cards, sheets, menús)", { it.surface }, { it.onSurface }),
    RolDeColor("surfaceVariant", "onSurfaceVariant", "Superficies con menos énfasis", { it.surfaceVariant }, { it.onSurfaceVariant }),
    RolDeColor("error", "onError", "Errores y estados de error", { it.error }, { it.onError })
)

/**
 * Apartado 5.2 — Los roles de color en Material 3.
 *
 * Cada fila tiene de FONDO el rol (p. ej. primary) y el TEXTO en su par (onPrimary).
 * Así se ve por qué existen los pares "on…": garantizan que el texto se lea.
 *
 * EXPERIMENTO: cambia de paleta, de modo claro/oscuro o activa los colores dinámicos
 * y vuelve aquí. Los nombres de los roles son los mismos; los colores, no.
 */
@Composable
fun PantallaPaleta() {
    val esquema = MaterialTheme.colorScheme

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(roles) { rol ->
            val fondo = rol.color(esquema)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(fondo, MaterialTheme.shapes.medium)
                    .border(1.dp, esquema.outline, MaterialTheme.shapes.medium)
                    .padding(16.dp)
            ) {
                val texto = rol.colorOn(esquema)
                Text("${rol.nombre}  ${hex(fondo)}", style = MaterialTheme.typography.titleMedium, color = texto)
                Text("texto en ${rol.nombreOn}  ${hex(texto)}", style = MaterialTheme.typography.bodyMedium, color = texto)
                Text(rol.uso, style = MaterialTheme.typography.bodySmall, color = texto)
            }
        }
        item {
            Text(
                "outline (bordes y separadores): es el color del borde de cada fila.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/** Color → "#1565C0" */
private fun hex(color: Color): String = "#%06X".format(color.toArgb() and 0xFFFFFF)

@Preview(name = "Claro", showBackground = true)
@Preview(name = "Oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PantallaPaletaPreview() {
    MiAppTheme(coloresDinamicos = false) {
        PantallaPaleta()
    }
}
