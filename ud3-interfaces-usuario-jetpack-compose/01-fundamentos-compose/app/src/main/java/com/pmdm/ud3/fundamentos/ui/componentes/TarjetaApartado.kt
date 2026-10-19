package com.pmdm.ud3.fundamentos.ui.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Tarjeta que envuelve cada apartado de la lección.
 *
 * Fíjate en el último parámetro: `contenido: @Composable () -> Unit`.
 * Es una función @Composable que recibimos como parámetro. Así cada apartado
 * "rellena" la tarjeta con lo que quiera. Es el mismo mecanismo que usan
 * Column { ... }, Button { ... } o Card { ... }: ¡también reciben una lambda @Composable!
 */
@Composable
fun TarjetaApartado(
    numero: String,
    titulo: String,
    explicacion: String,
    contenido: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$numero · $titulo",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = explicacion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            contenido()
        }
    }
}
