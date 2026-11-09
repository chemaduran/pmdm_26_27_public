package com.pmdm.ud3.eventos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pmdm.ud3.eventos.pantallas.ListaInteractivaCompleta
import com.pmdm.ud3.eventos.pantallas.PantallaAccesibilidad
import com.pmdm.ud3.eventos.pantallas.PantallaClicks
import com.pmdm.ud3.eventos.pantallas.PantallaGestos
import com.pmdm.ud3.eventos.pantallas.PantallaPullToRefresh
import com.pmdm.ud3.eventos.pantallas.PantallaScroll
import com.pmdm.ud3.eventos.pantallas.PantallaSwipe
import com.pmdm.ud3.eventos.pantallas.PantallaTeclado
import com.pmdm.ud3.eventos.pantallas.PantallaZoom

/** Una demo por apartado de la lección: la ruta, el texto del menú y su icono. */
private enum class Demo(val ruta: String, val titulo: String, val descripcion: String, val icono: ImageVector) {
    CLICKS("clicks", "6.2 · Clicks y pulsaciones", "clickable, ripple, long press, doble tap", Icons.Default.TouchApp),
    GESTOS("gestos", "6.3 · Arrastrar y tocar", "detectDragGestures, detectTapGestures", Icons.Default.PanTool),
    ZOOM("zoom", "6.3 · Zoom y rotación", "detectTransformGestures (dos dedos)", Icons.Default.ZoomIn),
    SWIPE("swipe", "6.4 · Deslizar para eliminar", "SwipeToDismissBox", Icons.Default.Swipe),
    SCROLL("scroll", "6.5 · Scroll", "verticalScroll, LazyListState, scroll programático", Icons.Default.SwapVert),
    TECLADO("teclado", "6.6 · Teclado y foco", "KeyboardOptions, ImeAction, FocusManager", Icons.Default.Keyboard),
    REFRESH("refresh", "6.7 · Pull to refresh", "PullToRefreshBox", Icons.Default.Refresh),
    ACCESIBILIDAD("accesibilidad", "6.8 · Accesibilidad", "contentDescription, semantics, 48dp", Icons.Default.Accessibility),
    COMPLETO("completo", "6.9 · Lista interactiva completa", "Búsqueda + selección con long press", Icons.Default.Checklist)
}

/**
 * Navegación de la app (lección 4): un menú y una pantalla por demo.
 * Todas las pantallas reciben `onVolver` y el NavHost decide qué hace.
 */
@Composable
fun AppEventos() {
    val navController = rememberNavController()
    val volver: () -> Unit = { navController.popBackStack() }

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            PantallaMenu(onAbrir = { demo -> navController.navigate(demo.ruta) })
        }
        composable(Demo.CLICKS.ruta) { PantallaClicks(onVolver = volver) }
        composable(Demo.GESTOS.ruta) { PantallaGestos(onVolver = volver) }
        composable(Demo.ZOOM.ruta) { PantallaZoom(onVolver = volver) }
        composable(Demo.SWIPE.ruta) { PantallaSwipe(onVolver = volver) }
        composable(Demo.SCROLL.ruta) { PantallaScroll(onVolver = volver) }
        composable(Demo.TECLADO.ruta) { PantallaTeclado(onVolver = volver) }
        composable(Demo.REFRESH.ruta) { PantallaPullToRefresh(onVolver = volver) }
        composable(Demo.ACCESIBILIDAD.ruta) { PantallaAccesibilidad(onVolver = volver) }
        composable(Demo.COMPLETO.ruta) { ListaInteractivaCompleta(onVolver = volver) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PantallaMenu(onAbrir: (Demo) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Lección 6 · Eventos") }) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(Demo.entries) { demo ->
                ListItem(
                    headlineContent = { Text(demo.titulo) },
                    supportingContent = { Text(demo.descripcion) },
                    leadingContent = {
                        Icon(demo.icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    trailingContent = {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                    },
                    // El patrón de toda la lección: el evento es una lambda
                    modifier = Modifier.clickable { onAbrir(demo) }
                )
                HorizontalDivider()
            }
        }
    }
}
