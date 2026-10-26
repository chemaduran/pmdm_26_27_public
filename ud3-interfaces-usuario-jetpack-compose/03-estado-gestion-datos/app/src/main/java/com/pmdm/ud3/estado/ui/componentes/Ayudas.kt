package com.pmdm.ud3.estado.ui.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Título de cada bloque de ejemplo, con su texto explicativo. */
@Composable
fun TituloSeccion(titulo: String, explicacion: String? = null) {
    Column(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) {
        Text(titulo, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        if (explicacion != null) {
            Text(
                explicacion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Dibuja un borde alrededor de un ejemplo para que se vean los límites
 * del contenedor (así se entiende mejor qué hacen Arrangement y Alignment).
 */
@Composable
fun Muestra(contenido: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
    ) {
        contenido()
    }
}
