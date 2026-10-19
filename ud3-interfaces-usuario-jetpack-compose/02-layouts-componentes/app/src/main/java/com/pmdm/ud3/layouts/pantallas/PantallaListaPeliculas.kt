package com.pmdm.ud3.layouts.pantallas

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.ud3.layouts.datos.Pelicula
import com.pmdm.ud3.layouts.datos.peliculasDeEjemplo
import com.pmdm.ud3.layouts.ui.theme.MiAppTheme

/**
 * Apartado 2.5 — Ejemplo completo: pantalla de lista de películas.
 *
 * Es el código de los apuntes con un único añadido: dos parámetros de tipo lambda
 * (onAnadir y onVaciar) para que el FAB y el botón de la papelera hagan algo de verdad.
 *
 *  - Pulsa "Añadir" → aparece una película nueva al final de la lista.
 *  - Pulsa la papelera → la lista se vacía y verás el "estado vacío" del `if`.
 *
 * ¿Por qué la pantalla no modifica la lista ella misma? Porque solo la MUESTRA:
 * la lista vive en AppLayouts y aquí solo "avisamos" de lo que ha pasado.
 * A esto se le llama elevación de estado (state hoisting) y es lo primero de la lección 3.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListaPeliculas(
    peliculas: List<Pelicula>,
    onAnadir: () -> Unit = {},
    onVaciar: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Películas (${peliculas.size})") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = onVaciar) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = "Vaciar lista")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAnadir,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Añadir") }
            )
        }
    ) { paddingValues ->
        if (peliculas.isEmpty()) {
            // Estado vacío
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Movie,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No hay películas",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(peliculas, key = { it.id }) { pelicula ->
                    PeliculaItem(pelicula)
                }
            }
        }
    }
}

@Composable
fun PeliculaItem(pelicula: Pelicula) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Póster placeholder
            Box(
                modifier = Modifier
                    .size(60.dp, 80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("🎬", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // weight(1f): la columna de texto ocupa todo el hueco que dejan el póster y el botón
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pelicula.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${pelicula.director} · ${pelicula.anio}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFB300)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.1f", pelicula.puntuacion),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
            }
        }
    }
}

@Preview(showBackground = true, name = "Con películas")
@Composable
fun PantallaListaPeliculasPreview() {
    MiAppTheme {
        PantallaListaPeliculas(peliculas = peliculasDeEjemplo)
    }
}

@Preview(showBackground = true, name = "Estado vacío")
@Composable
fun PantallaListaPeliculasVaciaPreview() {
    MiAppTheme {
        PantallaListaPeliculas(peliculas = emptyList())
    }
}
