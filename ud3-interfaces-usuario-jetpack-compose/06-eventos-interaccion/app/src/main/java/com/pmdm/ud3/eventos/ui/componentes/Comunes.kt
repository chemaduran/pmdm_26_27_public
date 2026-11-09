package com.pmdm.ud3.eventos.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Plantilla común: Scaffold + TopAppBar con flecha de volver. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDemo(
    titulo: String,
    onVolver: () -> Unit,
    contenido: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            content = contenido
        )
    }
}

/**
 * Añade un evento al principio del registro (y guarda solo los 6 últimos).
 * SnapshotStateList es la lista que devuelve mutableStateListOf(): al modificarla, se recompone.
 */
fun SnapshotStateList<String>.anotar(evento: String) {
    add(0, evento)
    if (size > 6) removeRange(6, size)
}

/** Panel que muestra los últimos eventos recibidos: así VES qué lambda se ha ejecutado. */
@Composable
fun PanelEventos(eventos: List<String>, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.inverseSurface,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .heightIn(min = 60.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                "Registro de eventos",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
            if (eventos.isEmpty()) {
                Text("(todavía nada: ¡interactúa!)", color = MaterialTheme.colorScheme.inverseOnSurface)
            }
            eventos.forEachIndexed { i, evento ->
                Text(
                    text = (if (i == 0) "▶ " else "   ") + evento,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    }
}
