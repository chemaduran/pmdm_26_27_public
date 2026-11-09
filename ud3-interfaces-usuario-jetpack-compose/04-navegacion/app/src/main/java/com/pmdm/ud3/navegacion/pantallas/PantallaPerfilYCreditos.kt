package com.pmdm.ud3.navegacion.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.navegacion.navegacion.Creditos
import com.pmdm.ud3.navegacion.ui.theme.MiAppTheme

/**
 * Pantalla de perfil: una de las tres secciones de la barra inferior.
 * El botón "Cerrar sesión" vuelve al login vaciando toda la pila.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPerfil(
    onAbrirMenu: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Ana García", style = MaterialTheme.typography.titleLarge)
                    Text("Cinéfila desde 1999", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Text(
                "Cerrar sesión hace navigate(\"login\") { popUpTo(graph.id) { inclusive = true } }: " +
                    "vacía TODA la pila. Pulsa atrás después: saldrás de la app.",
                style = MaterialTheme.typography.bodyMedium
            )
            Button(
                onClick = onCerrarSesion,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

/**
 * Pantalla de la ruta TYPE-SAFE. Compárala con PantallaDetalle:
 *  - Detalle:  composable("detalle/{peliculaId}", arguments = listOf(navArgument(...)))
 *              y backStackEntry.arguments?.getInt("peliculaId")
 *  - Créditos: composable<Creditos> { ... } y backStackEntry.toRoute<Creditos>()
 * Aquí recibimos directamente el objeto Creditos, con sus propiedades ya tipadas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCreditos(creditos: Creditos, onVolver: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Créditos") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Autor: ${creditos.autor}", style = MaterialTheme.typography.titleMedium)
            Text("Curso: ${creditos.curso}", style = MaterialTheme.typography.titleMedium)
            Text(
                "Esta pantalla usa una ruta type-safe: navigate(Creditos(autor, curso)). " +
                    "Si olvidas un argumento o pones un tipo incorrecto, el código no compila.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPerfilPreview() {
    MiAppTheme { PantallaPerfil(onAbrirMenu = {}, onCerrarSesion = {}) }
}
