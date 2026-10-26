package com.pmdm.ud3.estado.tareas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.ud3.estado.ui.theme.MiAppTheme

/**
 * Apartado 3.6 — Uso del ViewModel en Compose.
 *
 * Dos composables, como en los apuntes:
 *  - PantallaTareas: obtiene el ViewModel y "traduce" su estado en parámetros.
 *  - PantallaTareasContenido: NO conoce el ViewModel; recibe datos y lambdas (stateless).
 *
 * EXPERIMENTOS:
 *  1. Añade varias tareas y GIRA EL MÓVIL → siguen ahí (el ViewModel sobrevive a la rotación).
 *  2. Cambia a otra pestaña y vuelve → siguen ahí (el ViewModel vive mientras viva la Activity).
 *  3. Cierra la app del todo (deslízala en multitarea) → se pierden. Para guardarlas de
 *     verdad hace falta persistencia (Room / DataStore, en la UD4).
 */
@Composable
fun PantallaTareas(
    viewModel: TareasViewModel = viewModel()   // Se obtiene/crea automáticamente
) {
    // Recoger el estado como State de Compose: cada vez que el StateFlow emite, se recompone
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PantallaTareasContenido(
        tareas = uiState.tareas,
        nuevaTareaTexto = uiState.nuevaTareaTexto,
        onTextoChange = viewModel::actualizarTextoNuevaTarea,   // referencia a función (UD2)
        onAgregar = viewModel::agregarTarea,
        onToggle = viewModel::toggleTarea,
        onEliminar = viewModel::eliminarTarea
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareasContenido(
    tareas: List<Tarea>,
    nuevaTareaTexto: String,
    onTextoChange: (String) -> Unit,
    onAgregar: () -> Unit,
    onToggle: (Int) -> Unit,
    onEliminar: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("3.6 · Mis Tareas (${tareas.size})") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Campo para nueva tarea
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = nuevaTareaTexto,
                    onValueChange = onTextoChange,
                    label = { Text("Nueva tarea") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onAgregar() })
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onAgregar,
                    enabled = nuevaTareaTexto.isNotBlank()
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir")
                }
            }

            if (tareas.isEmpty()) {
                Text(
                    "No hay tareas. Escribe una arriba y pulsa + (o \"Listo\" en el teclado).",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Lista de tareas
            LazyColumn {
                items(tareas, key = { it.id }) { tarea ->
                    TareaItem(
                        tarea = tarea,
                        onToggle = { onToggle(tarea.id) },
                        onEliminar = { onEliminar(tarea.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun TareaItem(
    tarea: Tarea,
    onToggle: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = tarea.completada,
            onCheckedChange = { onToggle() }
        )
        Text(
            text = tarea.texto,
            modifier = Modifier.weight(1f),
            style = if (tarea.completada) {
                MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.LineThrough,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                MaterialTheme.typography.bodyLarge
            }
        )
        IconButton(onClick = onEliminar) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Eliminar",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

/** Como PantallaTareasContenido es stateless, la preview no necesita ViewModel. */
@Preview(showBackground = true)
@Composable
fun PantallaTareasContenidoPreview() {
    MiAppTheme {
        PantallaTareasContenido(
            tareas = listOf(
                Tarea(1, "Repasar la lección 3"),
                Tarea(2, "Hacer la práctica 3.1", completada = true),
                Tarea(3, "Probar a girar el móvil")
            ),
            nuevaTareaTexto = "",
            onTextoChange = {}, onAgregar = {}, onToggle = {}, onEliminar = {}
        )
    }
}
