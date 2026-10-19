package com.pmdm.ud3.layouts.pantallas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.layouts.datos.Pelicula
import com.pmdm.ud3.layouts.datos.contactosDeEjemplo
import com.pmdm.ud3.layouts.datos.imagenesDeEjemplo
import com.pmdm.ud3.layouts.datos.peliculasDeEjemplo
import com.pmdm.ud3.layouts.datos.peliculasPorGenero
import com.pmdm.ud3.layouts.ui.theme.MiAppTheme

/**
 * Apartado 2.2 — Listas con Lazy*.
 *
 * Elige el tipo de lista con los chips de arriba. Los cuatro composables
 * (ListaDeContactos, ListaConSecciones, CarruselDePeliculas y GaleriaDeImagenes)
 * son los de los apuntes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListas() {
    val tipos = listOf("LazyColumn", "Secciones", "LazyRow", "LazyVerticalGrid")
    var tipoSeleccionado by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("2.2 · Listas Lazy") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tipos.forEachIndexed { indice, tipo ->
                    FilterChip(
                        selected = indice == tipoSeleccionado,
                        onClick = { tipoSeleccionado = indice },
                        label = { Text(tipo) }
                    )
                }
            }
            HorizontalDivider()

            when (tipoSeleccionado) {
                0 -> {
                    Explicacion(
                        "100 contactos, pero LazyColumn solo compone los visibles. " +
                            "Abre Logcat (filtro \"UD3\") y haz scroll: verás cómo se componen sobre la marcha."
                    )
                    ListaDeContactos(contactosDeEjemplo)
                }
                1 -> {
                    Explicacion("item { } para una cabecera, items(lista) { } para varios elementos.")
                    ListaConSecciones(peliculasPorGenero)
                }
                2 -> {
                    Explicacion("Desliza horizontalmente el carrusel.")
                    CarruselDePeliculas(peliculasDeEjemplo)
                }
                3 -> GaleriaConSelector()
            }
        }
    }
}

@Composable
private fun Explicacion(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(16.dp)
    )
}

// ---------- Ejemplos de los apuntes ----------

@Composable
fun ListaDeContactos(contactos: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),           // Padding del contenido
        verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre items
    ) {
        // key: clave estable y única. Aquí el propio nombre sirve, porque no se repite.
        items(contactos, key = { it }) { contacto ->
            // Solo para la demostración: escribimos en Logcat cada vez que se compone un item
            Log.d("UD3", "Componiendo $contacto")
            Text(
                text = contacto,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
    }
}

@Composable
fun ListaConSecciones(peliculas: Map<String, List<String>>) {
    LazyColumn {
        peliculas.forEach { (genero, listaPeliculas) ->
            // Cabecera de sección
            item {
                Text(
                    text = genero,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            // Elementos de la sección
            items(listaPeliculas) { pelicula ->
                Text(
                    text = pelicula,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 4.dp)
                )
            }
            // Separador
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun CarruselDePeliculas(peliculas: List<Pelicula>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(peliculas) { pelicula ->
            Card(
                modifier = Modifier.width(140.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(pelicula.titulo, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

/** Añadimos un Switch para alternar entre las dos opciones de `columns` de los apuntes. */
@Composable
fun GaleriaConSelector() {
    var adaptativa by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (adaptativa) "GridCells.Adaptive(120.dp)" else "GridCells.Fixed(3)",
            modifier = Modifier.weight(1f)
        )
        Switch(checked = adaptativa, onCheckedChange = { adaptativa = it })
    }
    Explicacion("Gira el móvil con Adaptive activado: caben más columnas. Con Fixed siempre hay 3.")
    GaleriaDeImagenes(
        imagenes = imagenesDeEjemplo,
        columnas = if (adaptativa) GridCells.Adaptive(120.dp) else GridCells.Fixed(3)
    )
}

@Composable
fun GaleriaDeImagenes(imagenes: List<String>, columnas: GridCells = GridCells.Fixed(3)) {
    LazyVerticalGrid(
        columns = columnas,               // Fixed(3) = 3 columnas fijas; Adaptive(120.dp) = mín. 120dp
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(imagenes) { imagen ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)  // Cuadrado
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(imagen)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaListasPreview() {
    MiAppTheme {
        PantallaListas()
    }
}
