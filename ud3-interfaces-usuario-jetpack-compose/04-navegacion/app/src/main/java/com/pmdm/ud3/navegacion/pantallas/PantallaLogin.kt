package com.pmdm.ud3.navegacion.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.navegacion.ui.theme.MiAppTheme

/**
 * Apartado 4.3 — "Navegar reemplazando el back stack" (login → pantalla principal).
 *
 * EXPERIMENTO: pulsa "Entrar" y luego el botón ATRÁS del sistema.
 * No vuelves al login: sales de la app, porque el login ya no está en la pila.
 * Mira el panel "Back stack" de abajo antes y después de entrar.
 */
@Composable
fun PantallaLogin(onEntrar: () -> Unit) {
    var usuario by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🎬", style = MaterialTheme.typography.displayLarge)
        Text("Películas App", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Ruta actual: \"login\". Al entrar navegaremos a \"inicio\" con " +
                "popUpTo(\"login\") { inclusive = true }.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario (no se comprueba)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onEntrar, modifier = Modifier.fillMaxWidth()) {
            Text("Entrar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaLoginPreview() {
    MiAppTheme { PantallaLogin(onEntrar = {}) }
}
