package com.pmdm.ud3.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pmdm.ud3.layouts.datos.peliculasDeEjemplo
import com.pmdm.ud3.layouts.datos.peliculasParaAnadir
import com.pmdm.ud3.layouts.pantallas.PantallaComponentes
import com.pmdm.ud3.layouts.pantallas.PantallaLayouts
import com.pmdm.ud3.layouts.pantallas.PantallaListaPeliculas
import com.pmdm.ud3.layouts.pantallas.PantallaListas
import com.pmdm.ud3.layouts.ui.theme.MiAppTheme

/**
 * Apartado 2.4 — Scaffold + NavigationBar (apartado 2.3).
 *
 * La app es un Scaffold cuya barra inferior es la BarraNavegacionInferior de los apuntes.
 * Según la pestaña seleccionada mostramos una pantalla u otra con un simple `when`.
 *
 * (En la lección 4 cambiaremos este `when` por Navigation Compose, que además
 *  gestiona el botón "atrás". De momento, con esto es suficiente.)
 */
@Composable
fun AppLayouts() {
    var pestanaSeleccionada by remember { mutableIntStateOf(0) }

    // Lista de películas de la pestaña 4. La guardamos aquí para que no se pierda al
    // cambiar de pestaña (en la lección 3 entenderás exactamente por qué).
    var peliculas by remember { mutableStateOf(peliculasDeEjemplo) }

    Scaffold(
        bottomBar = {
            BarraNavegacionInferior(
                selectedIndex = pestanaSeleccionada,
                onItemSelected = { pestanaSeleccionada = it }
            )
        }
    ) { paddingValues ->
        // ⚠️ IMPORTANTE: usar paddingValues para que el contenido no quede tras la barra.
        // consumeWindowInsets() avisa a los Scaffold "de dentro" (cada pantalla tiene el
        // suyo con su TopAppBar) de que este espacio ya está reservado, para que no lo
        // vuelvan a sumar.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            when (pestanaSeleccionada) {
                0 -> PantallaLayouts()
                1 -> PantallaListas()
                2 -> PantallaComponentes()
                3 -> PantallaListaPeliculas(
                    peliculas = peliculas,
                    onAnadir = {
                        // Añade la siguiente película que aún no esté en la lista
                        val siguiente = peliculasParaAnadir.firstOrNull { it !in peliculas }
                        if (siguiente != null) peliculas = peliculas + siguiente
                    },
                    onVaciar = { peliculas = emptyList() }
                )
            }
        }
    }
}

/**
 * La BarraNavegacionInferior de los apuntes (apartado 2.3), con 4 secciones.
 * Fíjate en que NO guarda estado: recibe qué pestaña está seleccionada y
 * avisa hacia arriba cuando se pulsa otra (onItemSelected).
 */
@Composable
fun BarraNavegacionInferior(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
            label = { Text("Layouts") },
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
            label = { Text("Listas") },
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Widgets, contentDescription = null) },
            label = { Text("Componentes") },
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Movie, contentDescription = null) },
            label = { Text("Películas") },
            selected = selectedIndex == 3,
            onClick = { onItemSelected(3) }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppLayoutsPreview() {
    MiAppTheme {
        AppLayouts()
    }
}
