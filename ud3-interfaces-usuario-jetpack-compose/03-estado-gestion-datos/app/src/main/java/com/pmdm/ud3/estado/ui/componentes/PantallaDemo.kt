package com.pmdm.ud3.estado.ui.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Plantilla de las pantallas de demostración: Scaffold + TopAppBar + columna con scroll.
 * Así cada pantalla solo tiene que escribir su contenido.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDemo(
    titulo: String,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(titulo) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            content = contenido
        )
    }
}
