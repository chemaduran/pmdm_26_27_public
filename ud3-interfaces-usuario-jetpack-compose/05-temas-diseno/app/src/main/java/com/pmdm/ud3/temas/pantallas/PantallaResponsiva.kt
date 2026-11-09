package com.pmdm.ud3.temas.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.temas.ui.theme.MiAppTheme

data class Pelicula(val titulo: String, val anio: Int, val sinopsis: String)

private val peliculas = listOf(
    Pelicula("Interstellar", 2014, "Un grupo de exploradores viaja a través de un agujero de gusano en busca de un nuevo hogar para la humanidad."),
    Pelicula("Parásitos", 2019, "La familia Kim, todos desempleados, se interesa por la acaudalada familia Park."),
    Pelicula("El Padrino", 1972, "Don Vito Corleone es el respetado y temido jefe de una de las cinco familias de la mafia de Nueva York."),
    Pelicula("El viaje de Chihiro", 2001, "Chihiro, de diez años, queda atrapada en un mundo de espíritus y debe encontrar la forma de volver."),
    Pelicula("Origen", 2010, "Dom Cobb roba secretos del subconsciente mientras la gente sueña.")
)

/**
 * Apartado 5.7 — Diseño responsivo con BoxWithConstraints.
 *
 * BoxWithConstraints nos dice cuánto espacio tenemos (maxWidth) y decidimos el layout.
 *
 * EXPERIMENTO: GIRA EL MÓVIL. En vertical un teléfono tiene ~400dp de ancho (compacto)
 * y en horizontal ~900dp (expandido): la pantalla pasa de lista a lista + detalle.
 * En Android Studio, mira también las dos previews de abajo (móvil y tablet).
 */
@Composable
fun PantallaResponsiva() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val ancho = maxWidth   // lo guardamos: dentro de otros composables (Surface) no se puede leer maxWidth
        val esCompacto = ancho < 600.dp
        val esMedio = ancho in 600.dp..840.dp
        val categoria = when {
            esCompacto -> "COMPACTO (< 600dp) → móvil"
            esMedio -> "MEDIO (600–840dp) → tablet pequeña / móvil en horizontal"
            else -> "EXPANDIDO (> 840dp) → tablet / escritorio"
        }

        Column {
            Surface(color = MaterialTheme.colorScheme.tertiaryContainer) {
                Text(
                    text = "maxWidth = ${ancho.value.toInt()}dp · $categoria",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            ContenidoConPaddingAdaptativo {
                PantallaAdaptativa(peliculas)
            }
        }
    }
}

/** Ejemplo "lista + detalle adaptativo" de los apuntes. */
@Composable
fun PantallaAdaptativa(peliculas: List<Pelicula>) {
    // Película seleccionada (solo se usa en el modo de dos columnas)
    var seleccionada by rememberSaveable { mutableIntStateOf(0) }

    BoxWithConstraints {
        if (maxWidth > 840.dp) {
            // Tablet/escritorio: lista y detalle lado a lado
            Row(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(peliculas) { indice, pelicula ->
                        PeliculaItemCompacto(
                            pelicula = pelicula,
                            seleccionada = indice == seleccionada,
                            onClick = { seleccionada = indice }
                        )
                    }
                }
                VerticalDivider()
                Box(modifier = Modifier.weight(2f)) {
                    PantallaDetalle(peliculas[seleccionada])
                }
            }
        } else {
            // Móvil: solo lista (en una app real, el detalle iría en otra pantalla)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(peliculas) { _, pelicula ->
                    PeliculaItemCompleto(pelicula)
                    HorizontalDivider()
                }
            }
        }
    }
}

/** Padding adaptativo de los apuntes: más margen lateral cuanto más ancha es la pantalla. */
@Composable
fun ContenidoConPaddingAdaptativo(content: @Composable () -> Unit) {
    BoxWithConstraints {
        val paddingHorizontal: Dp = when {
            maxWidth > 840.dp -> 64.dp    // Tablet grande
            maxWidth > 600.dp -> 32.dp    // Tablet pequeña
            else -> 16.dp                 // Móvil
        }

        Box(modifier = Modifier.padding(horizontal = paddingHorizontal)) {
            content()
        }
    }
}

@Composable
private fun PeliculaItemCompacto(pelicula: Pelicula, seleccionada: Boolean, onClick: () -> Unit) {
    Text(
        text = pelicula.titulo,
        style = MaterialTheme.typography.titleMedium,
        color = if (seleccionada) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .background(if (seleccionada) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(16.dp)
    )
}

@Composable
private fun PeliculaItemCompleto(pelicula: Pelicula) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(pelicula.titulo, style = MaterialTheme.typography.titleMedium)
        Text("${pelicula.anio}", style = MaterialTheme.typography.bodySmall)
        Text(pelicula.sinopsis, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun PantallaDetalle(pelicula: Pelicula) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(pelicula.titulo, style = MaterialTheme.typography.headlineMedium)
        Text("Año: ${pelicula.anio}", style = MaterialTheme.typography.titleSmall)
        Text(pelicula.sinopsis, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 16.dp))
    }
}

@Preview(name = "Móvil (360dp)", widthDp = 360, heightDp = 640, showBackground = true)
@Preview(name = "Tablet (900dp)", widthDp = 900, heightDp = 600, showBackground = true)
@Composable
fun PantallaResponsivaPreview() {
    MiAppTheme(coloresDinamicos = false) {
        PantallaResponsiva()
    }
}
