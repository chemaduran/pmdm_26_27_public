package com.pmdm.ud3.eventos.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.eventos.ui.componentes.PanelEventos
import com.pmdm.ud3.eventos.ui.componentes.PantallaDemo
import com.pmdm.ud3.eventos.ui.componentes.anotar
import com.pmdm.ud3.eventos.ui.theme.MiAppTheme
import kotlin.math.roundToInt

/**
 * Apartado 6.3 — Gestos con pointerInput: arrastrar y tocar.
 */
@Composable
fun PantallaGestos(onVolver: () -> Unit) {
    val eventos = remember { mutableStateListOf<String>() }

    PantallaDemo(titulo = "6.3 · Arrastrar y tocar", onVolver = onVolver) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("detectDragGestures: arrastra el círculo azul", style = MaterialTheme.typography.titleMedium)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
            ) {
                EjemploGestos()
            }

            Text("detectTapGestures: toca la zona naranja", style = MaterialTheme.typography.titleMedium)
            Text(
                "En los apuntes cada evento hace println(...). Aquí lo mostramos en el registro.",
                style = MaterialTheme.typography.bodySmall
            )
            ZonaDeToques(onEvento = { eventos.anotar(it) })
            PanelEventos(eventos)
        }
    }
}

/** El EjemploGestos de los apuntes, tal cual. */
@Composable
fun EjemploGestos() {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(80.dp)
            .background(Color.Blue, CircleShape)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()          // "Este gesto es mío": nadie más lo procesa
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    )
}

/** "Ejemplo: detectar tap y long press" de los apuntes. */
@Composable
fun ZonaDeToques(onEvento: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0xFFFFB74D), RoundedCornerShape(12.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        onEvento("Tap en posición: ${formato(offset)}")
                    },
                    onDoubleTap = { offset ->
                        onEvento("Doble tap en: ${formato(offset)}")
                    },
                    onLongPress = { offset ->
                        onEvento("Long press en: ${formato(offset)}")
                    },
                    onPress = {
                        onEvento("Presionando...")
                        // Puedes esperar a que se suelte:
                        tryAwaitRelease()
                        onEvento("Soltado")
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text("Toca · doble toque · mantén", color = Color.Black)
    }
}

private fun formato(offset: Offset) = "(${offset.x.roundToInt()}, ${offset.y.roundToInt()}) px"

/**
 * Apartado 6.3 — Zoom y rotación con detectTransformGestures.
 * En el EMULADOR: mantén pulsada la tecla Ctrl y arrastra con el ratón para simular dos dedos.
 */
@Composable
fun PantallaZoom(onVolver: () -> Unit) {
    PantallaDemo(titulo = "6.3 · Zoom y rotación", onVolver = onVolver) {
        ImagenConZoom()
    }
}

/** La ImagenConZoom de los apuntes + un botón para reiniciar. */
@Composable
fun ImagenConZoom() {
    var escala by remember { mutableFloatStateOf(1f) }
    var rotacion by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    escala = (escala * zoom).coerceIn(0.5f, 5f)
                    rotacion += rotation
                    offset += pan
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .graphicsLayer(
                    scaleX = escala,
                    scaleY = escala,
                    rotationZ = rotacion
                )
                .size(200.dp)
                .background(Color.Cyan, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("🖼️ Zoom: ${String.format("%.1f", escala)}x", color = Color.Black)
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Pellizca con dos dedos para hacer zoom y girar. Con uno, mueve.\n" +
                    "Emulador: Ctrl + arrastrar con el ratón.",
                style = MaterialTheme.typography.bodySmall
            )
            OutlinedButton(onClick = {
                escala = 1f; rotacion = 0f; offset = Offset.Zero
            }) { Text("Reiniciar") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaGestosPreview() {
    MiAppTheme { PantallaGestos(onVolver = {}) }
}
