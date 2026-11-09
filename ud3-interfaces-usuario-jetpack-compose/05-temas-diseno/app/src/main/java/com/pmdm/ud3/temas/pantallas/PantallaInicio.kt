package com.pmdm.ud3.temas.pantallas

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import com.pmdm.ud3.temas.Rutas
import com.pmdm.ud3.temas.ui.theme.MiAppTheme
import com.pmdm.ud3.temas.ui.theme.Paleta

private data class Demo(val ruta: String, val titulo: String, val descripcion: String, val icono: ImageVector)

private val demos = listOf(
    Demo(Rutas.PALETA, "5.2 · Roles de color", "primary, surface, error... y sus pares on…", Icons.Default.Palette),
    Demo(Rutas.TIPOGRAFIA, "5.3 · Tipografía", "MiTipografia con la fuente Montserrat", Icons.Default.TextFields),
    Demo(Rutas.FORMAS, "5.4 · Formas", "MisFormas, CircleShape, CutCornerShape", Icons.Default.Interests),
    Demo(Rutas.USO_COLORES, "5.6 · Usar el tema bien", "Colores del tema vs. colores a fuego", Icons.Default.RuleFolder),
    Demo(Rutas.RESPONSIVO, "5.7 · Diseño responsivo", "BoxWithConstraints: móvil vs. tablet", Icons.Default.Devices),
    Demo(Rutas.NATURALEZA, "5.9 · Tema Naturaleza", "El ejemplo completo de los apuntes", Icons.Default.Park)
)

/**
 * Pantalla de inicio: ajustes del tema + menú con una demo por apartado.
 * Cambia los ajustes y entra en las demos: TODO se adapta, porque las pantallas
 * leen los colores, textos y formas de MaterialTheme, no los escriben a fuego.
 */
@Composable
fun PantallaInicio(
    modoOscuro: Boolean,
    onModoOscuroChange: (Boolean) -> Unit,
    coloresDinamicos: Boolean,
    onColoresDinamicosChange: (Boolean) -> Unit,
    paleta: Paleta,
    onPaletaChange: (Paleta) -> Unit,
    onAbrir: (String) -> Unit
) {
    val dinamicosDisponibles = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Ajustes del tema", style = MaterialTheme.typography.titleLarge)

                FilaSwitch(
                    texto = "Modo oscuro (5.5)",
                    detalle = "También con el icono ☀/🌙 de arriba",
                    activado = modoOscuro,
                    onCambio = onModoOscuroChange
                )
                FilaSwitch(
                    texto = "Colores dinámicos (5.2)",
                    detalle = if (dinamicosDisponibles)
                        "Paleta generada desde tu fondo de pantalla. Solo con \"Azul y naranja\"."
                    else "Requiere Android 12 o superior",
                    activado = coloresDinamicos && dinamicosDisponibles,
                    habilitado = dinamicosDisponibles && paleta == Paleta.AZUL,
                    onCambio = onColoresDinamicosChange
                )

                Text("Paleta", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Paleta.entries.forEach { opcion ->
                        FilterChip(
                            selected = paleta == opcion,
                            onClick = { onPaletaChange(opcion) },
                            label = { Text(opcion.nombre) }
                        )
                    }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            demos.forEachIndexed { indice, demo ->
                if (indice > 0) HorizontalDivider()
                ListItem(
                    headlineContent = { Text(demo.titulo) },
                    supportingContent = { Text(demo.descripcion) },
                    leadingContent = {
                        Icon(demo.icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    trailingContent = {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                    },
                    modifier = Modifier.clickable { onAbrir(demo.ruta) }
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.FormatPaint, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
            Text(
                "  Genera tus propias paletas con el Material Theme Builder (5.8).",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun FilaSwitch(
    texto: String,
    detalle: String,
    activado: Boolean,
    onCambio: (Boolean) -> Unit,
    habilitado: Boolean = true
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(texto, style = MaterialTheme.typography.bodyLarge)
            Text(detalle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = activado, onCheckedChange = onCambio, enabled = habilitado)
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    MiAppTheme(coloresDinamicos = false) {
        PantallaInicio(false, {}, false, {}, Paleta.AZUL, {}, {})
    }
}
