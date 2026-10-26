package com.pmdm.ud3.estado.peliculas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Apartado 3.7 — Uso de UiState en Compose con `when`.
 *
 * EXPERIMENTOS:
 *  1. Al entrar verás el CircularProgressIndicator 2 s (Loading) y luego la lista (Success).
 *  2. Activa "Simular fallo de red" y pulsa el botón de recargar (arriba a la derecha):
 *     Loading → Error. Pulsa "Reintentar" tras desactivar el switch → Success.
 *  3. Cambia de pestaña y vuelve: NO se recarga, el ViewModel ya tiene los datos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPeliculas(viewModel: PeliculasViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val simularFallo by viewModel.simularFallo.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("3.7 · UiState") },
                actions = {
                    IconButton(onClick = { viewModel.cargarPeliculas() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Simular fallo de red", modifier = Modifier.weight(1f))
                Switch(checked = simularFallo, onCheckedChange = viewModel::cambiarSimularFallo)
            }
            Text(
                text = "Estado actual: ${nombreDelEstado(uiState)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))

            // ---- Código de los apuntes ----
            when (val state = uiState) {
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.data) { pelicula ->
                            PeliculaItem(pelicula)
                        }
                    }
                }
                is UiState.Error -> {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("❌ ${state.mensaje}")
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.cargarPeliculas() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

private fun nombreDelEstado(estado: UiState<*>): String = when (estado) {
    is UiState.Loading -> "Loading"
    is UiState.Success -> "Success (${(estado.data as List<*>).size} elementos)"
    is UiState.Error -> "Error"
}

@Composable
fun PeliculaItem(pelicula: Pelicula) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(pelicula.titulo, style = MaterialTheme.typography.titleMedium)
            Text("${pelicula.anio}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
