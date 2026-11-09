package com.pmdm.ud3.eventos.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.eventos.ui.theme.MiAppTheme

/**
 * Apartado 6.9 — Ejemplo completo: lista interactiva.
 *
 * Junta casi todo lo de la lección:
 *  - Búsqueda con teclado (ImeAction.Search + clearFocus).
 *  - combinedClickable: un LONG PRESS entra en "modo selección".
 *  - En modo selección, un click normal marca/desmarca y aparece el Checkbox.
 *  - La TopAppBar cambia (título y botón de borrar) según haya selección.
 *
 * Añadidos respecto a los apuntes: flecha para volver al menú y una X para
 * salir del modo selección sin borrar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaInteractivaCompleta(onVolver: () -> Unit = {}) {
    var elementos by remember {
        mutableStateOf(List(20) { "Elemento ${it + 1}" })
    }
    var seleccionados by remember { mutableStateOf(setOf<String>()) }
    var busqueda by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // Estado derivado "a mano": se recalcula en cada recomposición a partir de dos estados
    val elementosFiltrados = elementos.filter {
        it.contains(busqueda, ignoreCase = true)
    }

    // Una sola función para alternar la selección (en los apuntes el código se repite 3 veces)
    fun alternar(elemento: String) {
        seleccionados = if (elemento in seleccionados) seleccionados - elemento
                        else seleccionados + elemento
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (seleccionados.isEmpty()) {
                        Text("Mi Lista")
                    } else {
                        Text("${seleccionados.size} seleccionados")
                    }
                },
                navigationIcon = {
                    if (seleccionados.isEmpty()) {
                        IconButton(onClick = onVolver) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    } else {
                        IconButton(onClick = { seleccionados = emptySet() }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancelar selección")
                        }
                    }
                },
                actions = {
                    if (seleccionados.isNotEmpty()) {
                        IconButton(onClick = {
                            elementos = elementos.filterNot { it in seleccionados }
                            seleccionados = emptySet()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar selección")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Barra de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (busqueda.isNotEmpty()) {
                        IconButton(onClick = { busqueda = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { focusManager.clearFocus() }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (seleccionados.isEmpty()) {
                Text(
                    "💡 Mantén pulsado un elemento para empezar a seleccionar.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Lista con selección
            LazyColumn {
                items(
                    items = elementosFiltrados,
                    key = { it }
                ) { elemento ->
                    val estaSeleccionado = elemento in seleccionados

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (estaSeleccionado)
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                else Color.Transparent
                            )
                            .combinedClickable(
                                onClick = {
                                    // Solo selecciona con click normal si ya estamos en modo selección
                                    if (seleccionados.isNotEmpty()) alternar(elemento)
                                },
                                onLongClick = { alternar(elemento) }
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (seleccionados.isNotEmpty()) {
                            Checkbox(
                                checked = estaSeleccionado,
                                onCheckedChange = { alternar(elemento) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = elemento,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaInteractivaCompletaPreview() {
    MiAppTheme { ListaInteractivaCompleta() }
}
