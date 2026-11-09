package com.pmdm.ud3.navegacion.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.navegacion.ui.theme.MiAppTheme

/**
 * Apartado 4.6 — Pantalla de Agregar. Código de los apuntes, sin cambios.
 *
 * Al guardar o cancelar, el NavHost hace popBackStack(): volvemos a la lista
 * y, como la lista de películas es estado observable, la nueva ya aparece.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregar(
    onGuardar: (String, Int, String) -> Unit,
    onCancelar: () -> Unit
) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var anio by rememberSaveable { mutableStateOf("") }
    var sinopsis by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva película") },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(Icons.Default.Close, contentDescription = "Cancelar")
                    }
                },
                actions = {
                    TextButton(
                        onClick = { onGuardar(titulo, anio.toIntOrNull() ?: 0, sinopsis) },
                        enabled = titulo.isNotBlank() && anio.isNotBlank()
                    ) {
                        Text("Guardar")
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = anio,
                onValueChange = { anio = it.filter { c -> c.isDigit() } },
                label = { Text("Año") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = sinopsis,
                onValueChange = { sinopsis = it },
                label = { Text("Sinopsis") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaAgregarPreview() {
    MiAppTheme { PantallaAgregar(onGuardar = { _, _, _ -> }, onCancelar = {}) }
}
