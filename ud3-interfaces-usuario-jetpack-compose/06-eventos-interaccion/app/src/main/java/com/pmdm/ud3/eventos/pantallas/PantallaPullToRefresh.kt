package com.pmdm.ud3.eventos.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.eventos.ui.componentes.PantallaDemo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Apartado 6.7 — Pull to refresh (tirar para actualizar).
 *
 * Arrastra la lista hacia ABAJO desde arriba del todo: aparece el indicador,
 * "cargamos" durante 1,5 s y se añaden noticias nuevas al principio.
 */
@Composable
fun PantallaPullToRefresh(onVolver: () -> Unit) {
    var numeroActualizacion by remember { mutableIntStateOf(1) }
    var elementos by remember { mutableStateOf(List(5) { "Noticia ${it + 1} (carga inicial)" }) }
    var estaCargando by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    PantallaDemo(titulo = "6.7 · Pull to refresh", onVolver = onVolver) {
        Column {
            Text(
                "Tira de la lista hacia abajo. Actualizaciones: ${numeroActualizacion - 1}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            ListaConPullRefresh(
                elementos = elementos,
                estaCargando = estaCargando,
                onRefrescar = {
                    // En una app real, esto lo haría el ViewModel pidiendo datos a internet
                    scope.launch {
                        estaCargando = true
                        delay(1_500)
                        val nuevas = List(3) { "Noticia nueva #$numeroActualizacion.${it + 1}" }
                        elementos = nuevas + elementos
                        numeroActualizacion++
                        estaCargando = false
                    }
                }
            )
        }
    }
}

/** La ListaConPullRefresh de los apuntes: no tiene estado, todo le llega por parámetros. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaConPullRefresh(
    elementos: List<String>,
    estaCargando: Boolean,
    onRefrescar: () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = estaCargando,
        onRefresh = onRefrescar,
        state = pullRefreshState
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(elementos) { elemento ->
                Text(
                    text = elemento,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                HorizontalDivider()
            }
        }
    }
}
