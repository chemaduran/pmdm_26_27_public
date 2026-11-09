package com.pmdm.ud3.navegacion.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import com.pmdm.ud3.navegacion.datos.peliculasIniciales
import com.pmdm.ud3.navegacion.datos.Pelicula
import com.pmdm.ud3.navegacion.ui.theme.MiAppTheme

/**
 * Apartado 4.6 — Pantalla de Detalle.
 *
 * Llega aquí con la ruta "detalle/{peliculaId}" (p. ej. "detalle/3").
 * El NavHost lee el argumento, busca la película y nos la pasa ya resuelta.
 *
 * Añadidos para experimentar con el back stack:
 *  - "Siguiente película": navega a otro detalle → se APILA encima de este.
 *  - "Volver a la lista": popBackStack(Rutas.LISTA, inclusive = false) → quita TODOS
 *    los detalles de golpe y deja la lista arriba.
 *  - La flecha ← y el botón ATRÁS del sistema: popBackStack() → quita solo uno.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    pelicula: Pelicula,
    onVolver: () -> Unit,
    onSiguiente: () -> Unit,
    onVolverALista: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pelicula.titulo) },
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
                .padding(16.dp)
        ) {
            Text(pelicula.titulo, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Año: ${pelicula.anio}", style = MaterialTheme.typography.titleSmall)
            Text("Género: ${pelicula.genero}", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text(pelicula.sinopsis, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Argumento recibido: peliculaId = ${pelicula.id}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onSiguiente, modifier = Modifier.weight(1f)) {
                    Text("Siguiente película")
                }
                OutlinedButton(onClick = onVolverALista, modifier = Modifier.weight(1f)) {
                    Text("Volver a la lista")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaDetallePreview() {
    MiAppTheme {
        PantallaDetalle(peliculasIniciales.first(), onVolver = {}, onSiguiente = {}, onVolverALista = {})
    }
}
