package com.pmdm.ud3.navegacion.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.navegacion.datos.Pelicula
import com.pmdm.ud3.navegacion.datos.peliculasIniciales
import com.pmdm.ud3.navegacion.ui.theme.MiAppTheme

/**
 * Apartado 4.6 — Pantalla de Lista.
 *
 * Código de los apuntes + tres añadidos:
 *  - `filtro`: el argumento opcional que llega por la ruta (se muestra como un chip).
 *  - `onQuitarFiltro`: al pulsar la X del chip.
 *  - `onAbrirMenu`: el botón ☰ de la TopAppBar abre el menú lateral.
 *
 * Fíjate en que esta pantalla NO sabe nada de rutas ni del NavController:
 * solo dice "han pulsado la película 3" (onPeliculaClick(3)).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    peliculas: List<Pelicula>,
    filtro: String,
    onPeliculaClick: (Int) -> Unit,
    onAgregar: () -> Unit,
    onQuitarFiltro: () -> Unit,
    onAbrirMenu: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Películas") },
                navigationIcon = {
                    IconButton(onClick = onAbrirMenu) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregar) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (filtro.isNotEmpty()) {
                item {
                    InputChip(
                        selected = true,
                        onClick = onQuitarFiltro,
                        label = { Text("Filtro: $filtro") },
                        trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Quitar filtro") }
                    )
                }
            }
            items(peliculas, key = { it.id }) { pelicula ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPeliculaClick(pelicula.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(pelicula.titulo, style = MaterialTheme.typography.titleMedium)
                        Text("${pelicula.anio} · ${pelicula.genero}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaListaPreview() {
    MiAppTheme {
        PantallaLista(
            peliculas = peliculasIniciales,
            filtro = "Drama",
            onPeliculaClick = {}, onAgregar = {}, onQuitarFiltro = {}, onAbrirMenu = {}
        )
    }
}
