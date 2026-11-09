package com.pmdm.ud3.navegacion.navegacion

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph

/**
 * HERRAMIENTA DIDÁCTICA (no forma parte de los apuntes).
 *
 * Muestra el back stack en directo: la pila de pantallas que guarda el NavController.
 * La de la derecha (resaltada) es la que estás viendo; "atrás" la quita de la pila.
 *
 * Fíjate en cómo cambia al:
 *  - entrar desde el login (el login desaparece: popUpTo inclusive),
 *  - navegar a un detalle y pulsar "Siguiente" varias veces (se apilan),
 *  - pulsar "Volver a la lista" (popBackStack hasta la lista),
 *  - pulsar 3 veces con/sin launchSingleTop en Inicio.
 */
@Composable
fun PanelBackStack(navController: NavController, modifier: Modifier = Modifier) {
    val pila by navController.currentBackStack.collectAsState()
    // La pila también contiene el propio grafo (NavGraph): lo quitamos, solo queremos pantallas
    val pantallas = pila.filter { it.destination !is NavGraph }

    Surface(color = MaterialTheme.colorScheme.surfaceVariant, modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState(), reverseScrolling = true)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Back stack:", style = MaterialTheme.typography.labelMedium)
            pantallas.forEachIndexed { indice, entrada ->
                val esLaActual = indice == pantallas.lastIndex
                if (indice > 0) Text("›", style = MaterialTheme.typography.labelMedium)
                Text(
                    text = describirEntrada(entrada),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (esLaActual) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .background(
                            if (esLaActual) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

/**
 * Convierte una entrada de la pila en un texto legible, sustituyendo los argumentos:
 *   "detalle/{peliculaId}"  → "detalle/3"
 *   "lista?filtro={filtro}" → "lista?filtro=Drama"  (o "lista" si no hay filtro)
 *   "com.pmdm...Creditos/{autor}/{curso}" → "Creditos/…"
 */
private fun describirEntrada(entrada: NavBackStackEntry): String {
    val patron = entrada.destination.route ?: return "?"
    val argumentos = entrada.arguments
    val conValores = Regex("\\{(\\w+)\\}").replace(patron) { coincidencia ->
        @Suppress("DEPRECATION")   // Bundle.get() genérico: suficiente para mostrar el valor
        argumentos?.get(coincidencia.groupValues[1])?.toString() ?: ""
    }
    return conValores
        .substringAfterLast('.')            // rutas type-safe: quitamos el paquete
        .replace(Regex("\\?\\w+=$"), "")    // query parameter vacío: lo ocultamos
}
