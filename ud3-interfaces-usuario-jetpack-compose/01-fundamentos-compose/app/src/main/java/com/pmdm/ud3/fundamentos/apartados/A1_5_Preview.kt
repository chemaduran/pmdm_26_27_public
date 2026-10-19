package com.pmdm.ud3.fundamentos.apartados

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.fundamentos.ui.componentes.TarjetaApartado
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Apartado 1.5 — @Preview.
 *
 * ESTE FICHERO SE ENTIENDE MEJOR EN ANDROID STUDIO QUE EN EL MÓVIL:
 * ábrelo y pulsa el botón "Split" (arriba a la derecha del editor).
 * Verás un panel con TODAS las previews de abajo sin ejecutar la app.
 *
 * Prueba también:
 *  - El icono de "Interactive mode" de una preview → puedes pulsar botones.
 *  - El icono de "Run Preview" → despliega solo ese composable en el emulador.
 */

/** Un composable pequeño que usa colores del tema, para que se note el modo oscuro. */
@Composable
fun MensajeDelTema() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Soy un composable con preview",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Mis colores salen del tema, así que cambio en modo oscuro.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun ApartadoPreview() {
    TarjetaApartado(
        numero = "1.5",
        titulo = "Preview",
        explicacion = "Abre A1_5_Preview.kt en Android Studio con la vista Split: este mismo " +
            "composable aparece en varias previews (claro, oscuro, ancho fijo, pantalla completa)."
    ) {
        MensajeDelTema()
    }
}

// --- Las variantes de @Preview de los apuntes ---

// Preview con fondo
@Preview(showBackground = true, name = "1 · Con fondo")
@Composable
fun MensajePreviewFondo() {
    MiAppTheme { MensajeDelTema() }
}

// Preview con tamaño de dispositivo (muestra barra de estado y de navegación)
@Preview(showSystemUi = true, name = "2 · Dispositivo completo")
@Composable
fun MensajePreviewDispositivo() {
    MiAppTheme { MensajeDelTema() }
}

// Preview con ancho fijo: útil para ver cómo se adapta en pantallas estrechas
@Preview(widthDp = 200, showBackground = true, name = "3 · Ancho 200dp")
@Composable
fun MensajePreviewEstrecho() {
    MiAppTheme { MensajeDelTema() }
}

// Múltiples previews combinadas sobre la MISMA función: claro y oscuro a la vez
@Preview(name = "4 · Claro", showBackground = true)
@Preview(name = "5 · Oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MensajePreviewMultiple() {
    MiAppTheme { MensajeDelTema() }
}
