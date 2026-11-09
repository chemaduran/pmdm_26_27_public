package com.pmdm.ud3.temas.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.temas.ui.theme.MiAppTheme

/**
 * Apartado 5.4 — Formas personalizadas.
 *
 * Primero, las tres formas del tema (MisFormas en Shape.kt).
 * Después, cómo las usan los componentes y las formas "sueltas" de los apuntes.
 */
@Composable
fun PantallaFormas() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Formas del tema (MisFormas)", style = MaterialTheme.typography.titleLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MuestraForma("small\n4.dp", MaterialTheme.shapes.small)
            MuestraForma("medium\n12.dp", MaterialTheme.shapes.medium)
            MuestraForma("large\n16.dp", MaterialTheme.shapes.large)
        }

        Text("Los componentes usan el tema automáticamente", style = MaterialTheme.typography.titleLarge)
        // Card usa shapes.medium por defecto; lo ponemos explícito como en los apuntes
        Card(shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth()) {
            Text("Card(shape = MaterialTheme.shapes.medium)", modifier = Modifier.padding(16.dp))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AssistChip(onClick = {}, label = { Text("Chip → small") })
            Button(onClick = {}) { Text("Button → full") }
        }

        Text("Formas específicas", style = MaterialTheme.typography.titleLarge)
        // Esquinas redondeadas solo arriba
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)", modifier = Modifier.padding(16.dp))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MuestraForma("CircleShape", CircleShape)
            MuestraForma("CutCorner\nShape(12.dp)", CutCornerShape(12.dp))
        }
    }
}

@Composable
private fun MuestraForma(nombre: String, forma: Shape) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(forma)
                .background(MaterialTheme.colorScheme.primary)
        )
        Text(nombre, style = MaterialTheme.typography.labelMedium)
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
fun PantallaFormasPreview() {
    MiAppTheme(coloresDinamicos = false) {
        PantallaFormas()
    }
}
