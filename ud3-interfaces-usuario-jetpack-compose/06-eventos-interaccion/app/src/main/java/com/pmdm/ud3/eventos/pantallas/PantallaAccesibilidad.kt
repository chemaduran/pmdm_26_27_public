package com.pmdm.ud3.eventos.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.eventos.ui.componentes.PantallaDemo

/**
 * Apartado 6.8 — Accesibilidad básica.
 *
 * PARA COMPROBARLO DE VERDAD, activa TalkBack (lector de pantalla):
 *   Ajustes > Accesibilidad > TalkBack.
 * Con TalkBack: un toque SELECCIONA y lee en voz alta; doble toque ACTIVA.
 * (En el emulador hay que instalar "Android Accessibility Suite" desde Play Store,
 *  o usa el Layout Inspector / Accessibility Scanner.)
 */
@Composable
fun PantallaAccesibilidad(onVolver: () -> Unit) {
    PantallaDemo(titulo = "6.8 · Accesibilidad", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Seccion("contentDescription") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // ✅ CON contentDescription: TalkBack dice "Marcar como favorito"
                    Icon(Icons.Default.Favorite, contentDescription = "Marcar como favorito")
                    Text("  ✅ TalkBack lee: \"Marcar como favorito\"")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // ✅ Si es puramente decorativo, usar null: TalkBack lo ignora
                    Icon(Icons.Default.Star, contentDescription = null)
                    Text("  ✅ Decorativo (null): TalkBack lo salta")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // ❌ MAL: en Compose es obligatorio pasar el parámetro, pero poner un texto
                    // inútil es igual de malo que no ponerlo
                    Icon(Icons.Default.Delete, contentDescription = "icono")
                    Text("  ❌ \"icono\": no dice QUÉ hace")
                }
            }

            Seccion("Semántica: mergeDescendants") {
                Text("Sin agrupar: TalkBack lee cada pieza por separado (3 paradas).", style = MaterialTheme.typography.bodySmall)
                FilaValoracion(agrupada = false)
                Text("Agrupada: lo lee todo de una vez (1 parada).", style = MaterialTheme.typography.bodySmall)
                FilaValoracion(agrupada = true)
            }

            Seccion("Semántica personalizada") {
                InterruptorPersonalizado()
            }

            Seccion("Tamaño mínimo táctil (48dp)") {
                Text(
                    "Los bordes rojos muestran el ÁREA QUE SE PUEDE PULSAR. Intenta acertar en " +
                        "el icono de la izquierda: cuesta mucho más.",
                    style = MaterialTheme.typography.bodySmall
                )
                AreasTactiles()
            }

            Seccion("Contraste") {
                Text(
                    "Usando los pares de MaterialTheme.colorScheme (primary/onPrimary...) el contraste " +
                        "está garantizado. Con colores propios, compruébalo en WebAIM Contrast Checker.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("✅ Bien", color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary).padding(12.dp))
                    Text("❌ Mal", color = Color(0xFFBDBDBD),
                        modifier = Modifier.background(Color(0xFFE0E0E0)).padding(12.dp))
                }
            }
        }
    }
}

@Composable
private fun Seccion(titulo: String, contenido: @Composable () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            contenido()
        }
    }
}

/** El ejemplo de "Agrupar información para el lector de pantalla". */
@Composable
private fun FilaValoracion(agrupada: Boolean) {
    Row(
        modifier = if (agrupada) Modifier.semantics(mergeDescendants = true) { } else Modifier,
        // El lector lee todo el Row como una sola unidad
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300))
        Text("4.5 estrellas")
        Spacer(Modifier.width(4.dp))
        Text("(123 valoraciones)")
    }
}

/**
 * Un "interruptor" hecho a mano con un Box. Sin semántica, TalkBack no sabría
 * que es un botón ni si está activado. Con semantics { } se lo contamos.
 */
@Composable
private fun InterruptorPersonalizado() {
    var activado by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(width = 160.dp, height = 56.dp)
            .background(
                if (activado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.shapes.medium
            )
            .clickable { activado = !activado }
            .semantics {
                contentDescription = "Notificaciones"
                role = Role.Switch
                stateDescription = if (activado) "Activadas" else "Desactivadas"
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            if (activado) "🔔 Activadas" else "🔕 Desactivadas",
            color = if (activado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Text(
        "TalkBack dirá: \"Notificaciones, ${if (activado) "Activadas" else "Desactivadas"}, interruptor\"",
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun AreasTactiles() {
    var pulsaciones by remember { mutableIntStateOf(0) }
    val bordeRojo = Modifier.border(1.dp, Color.Red)

    Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
        // ❌ Solo 24dp: el área táctil es igual de pequeña que el dibujo
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Info,
                contentDescription = "Información",
                modifier = Modifier
                    .then(bordeRojo)
                    .clickable { pulsaciones++ }
                    .size(24.dp)
            )
            Text("24dp ❌", style = MaterialTheme.typography.labelSmall)
        }
        // ✅ El dibujo sigue siendo de 24dp, pero el área táctil es de 48dp
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Info,
                contentDescription = "Información",
                modifier = Modifier
                    .minimumInteractiveComponentSize()     // Automáticamente 48dp
                    .then(bordeRojo)
                    .clickable { pulsaciones++ }
            )
            Text("48dp ✅", style = MaterialTheme.typography.labelSmall)
        }
        // ✅ IconButton ya cumple los 48dp por sí solo
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { pulsaciones++ }, modifier = bordeRojo) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar")
            }
            Text("IconButton ✅", style = MaterialTheme.typography.labelSmall)
        }
    }
    Text("Pulsaciones acertadas: $pulsaciones")
}
