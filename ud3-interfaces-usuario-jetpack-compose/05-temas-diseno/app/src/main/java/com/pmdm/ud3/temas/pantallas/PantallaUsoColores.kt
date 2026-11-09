package com.pmdm.ud3.temas.pantallas

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.temas.ui.theme.MiAppTheme

/**
 * Apartado 5.6 — Usar colores del tema correctamente.
 *
 * EXPERIMENTO: pulsa el icono 🌙 de la barra superior.
 *  - TarjetaCorrecta cambia de colores y sigue siendo legible.
 *  - TarjetaIncorrecta sigue blanca con texto negro: rompe el modo oscuro.
 */
@Composable
fun PantallaUsoColores() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("✅ Con colores del tema", style = MaterialTheme.typography.titleLarge)
        TarjetaCorrecta()

        Text("❌ Con colores a fuego (hardcodeados)", style = MaterialTheme.typography.titleLarge)
        TarjetaIncorrecta()

        Text(
            "📌 Regla: usa MaterialTheme.colorScheme.xxx en lugar de Color.White, Color.Black...",
            style = MaterialTheme.typography.bodyMedium
        )

        Text("Apartado 5.5 · TarjetaPelicula", style = MaterialTheme.typography.titleLarge)
        Text(
            "Abre este fichero en Android Studio (vista Split): tiene una preview en claro y " +
                "otra en oscuro de esta tarjeta.",
            style = MaterialTheme.typography.bodyMedium
        )
        TarjetaPelicula("Interstellar", 2014, "Ciencia ficción")
    }
}

@Composable
fun TarjetaCorrecta() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = "Título",
            color = MaterialTheme.colorScheme.onSurfaceVariant,  // ← Se adapta al tema
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun TarjetaIncorrecta() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White     // ← NO se adapta al modo oscuro
        )
    ) {
        Text(
            text = "Título",
            color = Color.Black,             // ← Ilegible en modo oscuro sobre fondo oscuro
            modifier = Modifier.padding(16.dp)
        )
    }
}

/** La TarjetaPelicula de la lección 2, que aquí usamos para las previews del apartado 5.5. */
@Composable
fun TarjetaPelicula(titulo: String, anio: Int, genero: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium)
            Row {
                Text("$anio", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(8.dp))
                AssistChip(onClick = { }, label = { Text(genero) })
            }
        }
    }
}

// Preview en ambos modos (apartado 5.5)
@Preview(name = "Claro", showBackground = true)
@Preview(name = "Oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TarjetaPreview() {
    MiAppTheme(coloresDinamicos = false) {
        TarjetaPelicula("Interstellar", 2014, "Ciencia ficción")
    }
}

@Preview(name = "Correcta vs incorrecta · Oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CorrectaIncorrectaPreview() {
    MiAppTheme(coloresDinamicos = false) {
        Column(Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TarjetaCorrecta()
            TarjetaIncorrecta()
        }
    }
}
