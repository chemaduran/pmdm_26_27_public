package com.pmdm.ud3.layouts.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.layouts.ui.componentes.Muestra
import com.pmdm.ud3.layouts.ui.componentes.TituloSeccion
import com.pmdm.ud3.layouts.ui.theme.MiAppTheme

/**
 * Apartado 2.1 — Layouts principales: Column, Row, Box, Arrangement, Alignment y weight.
 *
 * Los tres primeros ejemplos son los de los apuntes, sin cambios.
 * Después hay dos "exploradores" interactivos: elige un Arrangement / Alignment
 * con los chips y mira cómo se recolocan los elementos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLayouts() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("2.1 · Layouts") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TituloSeccion("Column", "Apila los elementos uno debajo de otro.")
            Muestra { EjemploColumn() }

            TituloSeccion("Row", "Coloca los elementos uno al lado del otro.")
            Muestra { EjemploRow() }

            TituloSeccion("Box", "Superpone los elementos: el último se dibuja encima.")
            EjemploBox()

            TituloSeccion("Explorador de Arrangement", "Distribución en el eje PRINCIPAL de un Row.")
            ExploradorArrangement()

            TituloSeccion("Explorador de Alignment", "Alineación en el eje SECUNDARIO de una Column.")
            ExploradorAlignment()

            TituloSeccion("weight", "Rojo weight(1f) = 1/3 del ancho; azul weight(2f) = 2/3.")
            EjemploWeight()
        }
    }
}

// ---------- Ejemplos de los apuntes ----------

@Composable
fun EjemploColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),  // Espacio entre elementos
        horizontalAlignment = Alignment.CenterHorizontally  // Centrado horizontal
    ) {
        Text("Primero")
        Text("Segundo")
        Text("Tercero")
    }
}

@Composable
fun EjemploRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,  // Distribuir espacio
        verticalAlignment = Alignment.CenterVertically     // Centrado vertical
    ) {
        Text("Izquierda")
        Text("Centro")
        Text("Derecha")
    }
}

@Composable
fun EjemploBox() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text("Fondo", modifier = Modifier.align(Alignment.TopStart))
        Icon(
            Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        Text("Centro")  // Se dibuja encima del "Fondo"
    }
}

@Composable
fun EjemploWeight() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(1f)       // 1/3 del espacio
                .height(50.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .weight(2f)       // 2/3 del espacio
                .height(50.dp)
                .background(Color.Blue)
        )
    }
}

// ---------- Exploradores interactivos ----------

/** Las opciones de la tabla "Arrangement" de los apuntes (versión horizontal, para Row). */
private val opcionesArrangement: List<Pair<String, Arrangement.Horizontal>> = listOf(
    "Start" to Arrangement.Start,
    "Center" to Arrangement.Center,
    "End" to Arrangement.End,
    "SpaceBetween" to Arrangement.SpaceBetween,
    "SpaceEvenly" to Arrangement.SpaceEvenly,
    "SpaceAround" to Arrangement.SpaceAround,
    "spacedBy(8.dp)" to Arrangement.spacedBy(8.dp)
)

@Composable
fun ExploradorArrangement() {
    var seleccion by remember { mutableIntStateOf(0) }
    val (nombre, arrangement) = opcionesArrangement[seleccion]

    // Fila de chips con scroll horizontal (no caben todos en pantalla)
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        opcionesArrangement.forEachIndexed { indice, (etiqueta, _) ->
            FilterChip(
                selected = indice == seleccion,
                onClick = { seleccion = indice },
                label = { Text(etiqueta) }
            )
        }
    }
    Text(
        "Row(horizontalArrangement = Arrangement.$nombre)",
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(vertical = 4.dp)
    )
    Muestra {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = arrangement
        ) {
            CajaColor("A", Color(0xFFE57373))
            CajaColor("B", Color(0xFF81C784))
            CajaColor("C", Color(0xFF64B5F6))
        }
    }
}

private val opcionesAlignment: List<Pair<String, Alignment.Horizontal>> = listOf(
    "Start" to Alignment.Start,
    "CenterHorizontally" to Alignment.CenterHorizontally,
    "End" to Alignment.End
)

@Composable
fun ExploradorAlignment() {
    var seleccion by remember { mutableIntStateOf(0) }
    val (nombre, alignment) = opcionesAlignment[seleccion]

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        opcionesAlignment.forEachIndexed { indice, (etiqueta, _) ->
            FilterChip(
                selected = indice == seleccion,
                onClick = { seleccion = indice },
                label = { Text(etiqueta) }
            )
        }
    }
    Text(
        "Column(horizontalAlignment = Alignment.$nombre)",
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(vertical = 4.dp)
    )
    Muestra {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = alignment,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CajaColor("Item 1", Color(0xFFE57373))
            CajaColor("Item 2", Color(0xFF81C784))
            CajaColor("Item 3", Color(0xFF64B5F6))
        }
    }
}

@Composable
private fun CajaColor(texto: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(texto, color = Color.White)
    }
}

@Preview(showBackground = true, heightDp = 1400)
@Composable
fun PantallaLayoutsPreview() {
    MiAppTheme {
        PantallaLayouts()
    }
}
