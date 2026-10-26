package com.pmdm.ud3.estado

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pmdm.ud3.estado.pantallas.PantallaHoisting
import com.pmdm.ud3.estado.pantallas.PantallaRemember
import com.pmdm.ud3.estado.pantallas.PantallaSaveableDerivado
import com.pmdm.ud3.estado.peliculas.PantallaPeliculas
import com.pmdm.ud3.estado.tareas.PantallaTareas

/** Las pestañas de la app: una por cada bloque de la lección 3. */
private enum class Pestana(val titulo: String, val icono: ImageVector) {
    REMEMBER("remember", Icons.Default.Memory),
    SAVEABLE("Saveable", Icons.Default.ScreenRotation),
    HOISTING("Hoisting", Icons.Default.SwapVert),
    VIEWMODEL("ViewModel", Icons.Default.Checklist),
    UISTATE("UiState", Icons.Default.CloudDownload)
}

/**
 * Misma estructura que en la lección 2: Scaffold + NavigationBar + `when`.
 *
 * Detalle importante: aquí la pestaña seleccionada se guarda con rememberSaveable.
 * Así, si giras el móvil, sigues en la misma pestaña. (Prueba a cambiarlo por
 * remember y gira el móvil: volverás siempre a la primera.)
 *
 * EXPERIMENTO CLAVE DE ESTA LECCIÓN:
 * Cuando cambias de pestaña, la pantalla anterior SALE de la composición y todo
 * lo que guardaba con remember/rememberSaveable se pierde. En cambio, lo que
 * vive en un ViewModel (pestañas "ViewModel" y "UiState") se conserva.
 * ¡Compruébalo!
 */
@Composable
fun AppEstado() {
    var pestanaActual by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                Pestana.entries.forEachIndexed { indice, pestana ->
                    NavigationBarItem(
                        icon = { Icon(pestana.icono, contentDescription = null) },
                        label = { Text(pestana.titulo) },
                        selected = pestanaActual == indice,
                        onClick = { pestanaActual = indice }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            when (Pestana.entries[pestanaActual]) {
                Pestana.REMEMBER -> PantallaRemember()
                Pestana.SAVEABLE -> PantallaSaveableDerivado()
                Pestana.HOISTING -> PantallaHoisting()
                Pestana.VIEWMODEL -> PantallaTareas()
                Pestana.UISTATE -> PantallaPeliculas()
            }
        }
    }
}
