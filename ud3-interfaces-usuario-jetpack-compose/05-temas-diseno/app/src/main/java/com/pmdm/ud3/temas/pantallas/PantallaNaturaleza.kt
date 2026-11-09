package com.pmdm.ud3.temas.pantallas

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.temas.ui.theme.NaturalezaTheme

/**
 * Apartado 5.9 — Ejemplo completo: tema personalizado (AppNaturaleza de los apuntes).
 *
 * ¡Los temas se pueden ANIDAR! Aunque la app esté con la paleta "Azul y naranja",
 * todo lo que hay dentro de NaturalezaTheme { } usa la paleta verde.
 * Fíjate: la barra superior sigue azul (está fuera) y el contenido es verde (está dentro).
 *
 * Le pasamos modoOscuro para que siga el interruptor de la app (en los apuntes usa
 * el valor por defecto, isSystemInDarkTheme()).
 */
@Composable
fun PantallaNaturaleza(modoOscuro: Boolean) {
    NaturalezaTheme(darkTheme = modoOscuro) {
        AppNaturaleza()
    }
}

@Composable
fun AppNaturaleza() {
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "🌿 Explorar la Naturaleza",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    "Descubre rutas cercanas",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { }) {
                Text("Comenzar")
            }
        }
    }
}

@Preview(name = "Naturaleza claro", showBackground = true)
@Preview(name = "Naturaleza oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AppNaturalezaPreview() {
    NaturalezaTheme {
        AppNaturaleza()
    }
}
