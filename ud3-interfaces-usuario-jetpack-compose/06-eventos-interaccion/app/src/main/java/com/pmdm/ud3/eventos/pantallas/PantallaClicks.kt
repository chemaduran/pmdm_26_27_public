package com.pmdm.ud3.eventos.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.eventos.ui.componentes.PanelEventos
import com.pmdm.ud3.eventos.ui.componentes.PantallaDemo
import com.pmdm.ud3.eventos.ui.componentes.anotar
import com.pmdm.ud3.eventos.ui.theme.MiAppTheme

/**
 * Apartados 6.1 y 6.2 — Modelo de eventos, clicks y pulsaciones.
 *
 * Cada elemento de esta pantalla, al recibir un evento, ejecuta su lambda,
 * que simplemente ANOTA el evento en el registro de arriba. Así ves qué
 * parámetro (onClick, onLongClick, onDoubleClick...) se ha disparado.
 */
@Composable
fun PantallaClicks(onVolver: () -> Unit) {
    val eventos = remember { mutableStateListOf<String>() }

    PantallaDemo(titulo = "6.2 · Clicks", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PanelEventos(eventos)

            Text("Componentes que ya soportan click", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { eventos.anotar("Button → onClick") }) { Text("Botón") }
                IconButton(onClick = { eventos.anotar("IconButton → onClick") }) {
                    Icon(Icons.Default.Star, contentDescription = "Estrella")
                }
                Card(onClick = { eventos.anotar("Card → onClick") }) {
                    Text("Card clickable", modifier = Modifier.padding(12.dp))
                }
            }

            Text("Cualquier composable con Modifier.clickable", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Texto clickable",
                    modifier = Modifier.clickable { eventos.anotar("Text.clickable") }
                )
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Blue)
                        .clickable { eventos.anotar("Box azul.clickable") }
                )
                // Sin efecto ripple (fíjate en que al pulsar no aparece la "onda")
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFF2E7D32))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null    // Sin ripple
                        ) { eventos.anotar("Box verde (sin ripple)") },
                    contentAlignment = Alignment.Center
                ) { Text("sin\nripple", color = Color.White, style = MaterialTheme.typography.labelSmall) }
            }

            Text("interactionSource: saber si está pulsado", style = MaterialTheme.typography.titleMedium)
            Text("Mantén el dedo sobre la caja gris.", style = MaterialTheme.typography.bodySmall)
            BotonConEstado()

            Text("combinedClickable: click, long press y doble tap", style = MaterialTheme.typography.titleMedium)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp))
                    .combinedClickable(
                        onClick = { eventos.anotar("combinedClickable → onClick") },
                        onLongClick = { eventos.anotar("combinedClickable → onLongClick") },
                        onDoubleClick = { eventos.anotar("combinedClickable → onDoubleClick") }
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Pulsa, mantén pulsado o haz doble tap",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

/** El BotonConEstado de los apuntes: cambia de color mientras está pulsado. */
@Composable
fun BotonConEstado() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                if (isPressed) Color.DarkGray else Color.LightGray
            )
            .clickable(interactionSource = interactionSource, indication = null) { }
    ) {
        Text(
            if (isPressed) "Pulsado" else "Pulsar",
            color = if (isPressed) Color.White else Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaClicksPreview() {
    MiAppTheme { PantallaClicks(onVolver = {}) }
}
