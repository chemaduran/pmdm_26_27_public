package com.pmdm.ud3.navegacion.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.navegacion.datos.generos
import com.pmdm.ud3.navegacion.ui.theme.MiAppTheme

/**
 * Pantalla de inicio: un "laboratorio" para probar las operaciones de navegación.
 *
 * @param onVerLista       navegar a la lista, con un filtro opcional (null = sin filtro).
 * @param onNavegarTresVeces navegar 3 veces seguidas a la lista, con o sin launchSingleTop.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(
    onAbrirMenu: () -> Unit,
    onVerLista: (filtro: String?) -> Unit,
    onNavegarTresVeces: (usarSingleTop: Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio") },
                navigationIcon = {
                    IconButton(onClick = onAbrirMenu) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BloqueLaboratorio(
                titulo = "4.4 · Argumentos opcionales",
                explicacion = "La ruta es \"lista?filtro={filtro}\". Puedes navegar a \"lista\" " +
                    "(sin filtro) o a \"lista?filtro=Drama\" (con filtro)."
            ) {
                Button(onClick = { onVerLista(null) }, modifier = Modifier.fillMaxWidth()) {
                    Text("navigate(\"lista\")")
                }
                generos.forEach { genero ->
                    OutlinedButton(onClick = { onVerLista(genero) }, modifier = Modifier.fillMaxWidth()) {
                        Text("navigate(\"lista?filtro=$genero\")")
                    }
                }
            }

            BloqueLaboratorio(
                titulo = "4.3 · Evitar duplicados: launchSingleTop",
                explicacion = "Estos botones navegan 3 veces seguidas a la lista (como un usuario " +
                    "impaciente). Mira el panel Back stack y luego pulsa ATRÁS varias veces."
            ) {
                OutlinedButton(onClick = { onNavegarTresVeces(false) }, modifier = Modifier.fillMaxWidth()) {
                    Text("×3 SIN launchSingleTop → 3 listas apiladas")
                }
                Button(onClick = { onNavegarTresVeces(true) }, modifier = Modifier.fillMaxWidth()) {
                    Text("×3 CON launchSingleTop → solo 1 lista")
                }
            }

            BloqueLaboratorio(
                titulo = "4.5 · Navegación principal",
                explicacion = "Usa la barra inferior o el menú lateral (botón ☰ o desliza desde la " +
                    "izquierda). Cambiar de sección NO apila pantallas: fíjate en el panel."
            ) {}
        }
    }
}

@Composable
private fun BloqueLaboratorio(
    titulo: String,
    explicacion: String,
    contenido: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(titulo, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Text(explicacion, style = MaterialTheme.typography.bodyMedium)
            contenido()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    MiAppTheme {
        PantallaInicio(onAbrirMenu = {}, onVerLista = {}, onNavegarTresVeces = {})
    }
}
