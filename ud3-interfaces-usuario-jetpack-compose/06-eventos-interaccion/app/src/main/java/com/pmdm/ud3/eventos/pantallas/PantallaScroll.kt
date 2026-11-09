package com.pmdm.ud3.eventos.pantallas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.eventos.ui.componentes.PantallaDemo
import kotlinx.coroutines.launch

/**
 * Apartado 6.5 — Scroll.
 * Elige con los chips entre el scroll simple y el scroll de LazyColumn con LazyListState.
 */
@Composable
fun PantallaScroll(onVolver: () -> Unit) {
    var modo by remember { mutableIntStateOf(1) }

    PantallaDemo(titulo = "6.5 · Scroll", onVolver = onVolver) {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(selected = modo == 0, onClick = { modo = 0 }, label = { Text("Column + verticalScroll") })
                FilterChip(selected = modo == 1, onClick = { modo = 1 }, label = { Text("LazyColumn + estado") })
            }
            HorizontalDivider()
            if (modo == 0) ContenidoConScroll() else ListaConScrollProgramatico()
        }
    }
}

/** "Scroll simple" de los apuntes: sin verticalScroll, los elementos 20-49 no se podrían ver. */
@Composable
fun ContenidoConScroll() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())  // ← Habilita scroll
            .padding(16.dp)
    ) {
        repeat(50) { i ->
            Text("Elemento $i", modifier = Modifier.padding(8.dp))
        }
    }
}

/**
 * Une los dos ejemplos de los apuntes: "Scroll programático" y "Detectar posición de scroll".
 *
 *  - El panel superior muestra listState.firstVisibleItemIndex en directo.
 *  - El FAB "subir" solo aparece cuando has bajado más allá del elemento 3 (derivedStateOf).
 *  - El FAB pequeño de abajo baja al elemento 99 con animación.
 */
@Composable
fun ListaConScrollProgramatico() {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // ¿Es visible el primer elemento? derivedStateOf: el scroll cambia a cada píxel,
    // pero este Boolean solo cambia al cruzar el umbral → recomponemos mucho menos.
    val mostrarBotonSubir by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 3 }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
                Text(
                    "firstVisibleItemIndex = ${listState.firstVisibleItemIndex} · " +
                        "mostrarBotonSubir = $mostrarBotonSubir",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            LazyColumn(state = listState) {
                items(100) { i ->
                    Text("Elemento $i", modifier = Modifier.padding(16.dp))
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mostrar botón "subir" solo cuando se ha hecho scroll
            AnimatedVisibility(visible = mostrarBotonSubir) {
                FloatingActionButton(
                    onClick = {
                        // animateScrollToItem es una función suspend → necesita una corrutina
                        scope.launch { listState.animateScrollToItem(0) }   // Scroll suave al inicio
                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Subir")
                }
            }
            SmallFloatingActionButton(onClick = { scope.launch { listState.animateScrollToItem(99) } }) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Bajar al final")
            }
        }
    }
}
