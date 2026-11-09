package com.pmdm.ud3.eventos.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.eventos.ui.componentes.PantallaDemo

private val frutasIniciales = listOf(
    "🍎 Manzana", "🍌 Plátano", "🍇 Uvas", "🍊 Naranja", "🍓 Fresa",
    "🍍 Piña", "🥝 Kiwi", "🍒 Cerezas", "🍑 Melocotón", "🍉 Sandía"
)

/**
 * Apartado 6.4 — Swipe to dismiss (deslizar para eliminar).
 */
@Composable
fun PantallaSwipe(onVolver: () -> Unit) {
    var elementos by remember { mutableStateOf(frutasIniciales) }

    PantallaDemo(titulo = "6.4 · Deslizar para eliminar", onVolver = onVolver) {
        Column {
            Text(
                "Desliza una tarjeta de DERECHA a IZQUIERDA para eliminarla. " +
                    "Hacia la derecha no hace nada (enableDismissFromStartToEnd = false).",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            OutlinedButton(
                onClick = { elementos = frutasIniciales },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) { Text("Restaurar la lista (${elementos.size}/${frutasIniciales.size})") }

            ListaConSwipe(
                elementos = elementos,
                onEliminar = { elemento -> elementos = elementos - elemento }
            )
        }
    }
}

/**
 * La ListaConSwipe de los apuntes con UN cambio importante:
 * onEliminar recibe el ELEMENTO, no su índice.
 *
 * ¿Por qué? Al borrar un elemento, todos los de debajo cambian de posición: el índice
 * que tenía cada uno ya no es el suyo, y es fácil acabar borrando otro elemento
 * (sobre todo si el índice se queda "guardado" dentro de un estado recordado).
 * Identificar por el propio elemento (o por su id) es seguro.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaConSwipe(
    elementos: List<String>,
    onEliminar: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(elementos, key = { it }) { elemento ->
            // ⚠️ CAMBIO DE API: en los apuntes se usa
            //     rememberSwipeToDismissBoxState(confirmValueChange = { value -> ... })
            // Desde Material 3 1.4 `confirmValueChange` está obsoleto (deprecated): el aviso
            // se hace con el parámetro `onDismiss` de SwipeToDismissBox (más abajo).
            // La idea es la misma: una lambda que recibe hacia dónde se ha deslizado.
            val dismissState = rememberSwipeToDismissBoxState()

            SwipeToDismissBox(
                state = dismissState,
                onDismiss = { value ->
                    if (value == SwipeToDismissBoxValue.EndToStart) {
                        onEliminar(elemento)
                    }
                },
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red, MaterialTheme.shapes.medium)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.White
                        )
                    }
                },
                enableDismissFromStartToEnd = false
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = elemento,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
