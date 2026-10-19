package com.pmdm.ud3.fundamentos.apartados

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.fundamentos.ui.componentes.TarjetaApartado
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Apartado 1.4 — Primer composable.
 *
 * En los apuntes se llama `PantallaInicio`; aquí lo llamamos PrimerComposable para
 * no confundirlo con el del apartado 1.2.
 *
 * La otra mitad del apartado 1.4 (la Activity) está en MainActivity.kt.
 *
 * Nota: en los apuntes usa Modifier.fillMaxSize(); aquí usamos fillMaxWidth()
 * porque está dentro de una tarjeta y no queremos que ocupe toda la pantalla.
 */
@Composable
fun PrimerComposable() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¡Hola, Compose!",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Abre la pestaña Logcat de Android Studio y filtra por "UD3"
            Log.d("UD3", "Has pulsado Empezar")
        }) {
            Text("Empezar")
        }
    }
}

@Composable
fun ApartadoEstructuraMinima() {
    TarjetaApartado(
        numero = "1.4",
        titulo = "Estructura mínima de una app",
        explicacion = "La Activity (MainActivity.kt) llama a setContent { MiAppTheme { Surface { ... } } }. " +
            "Dentro va el primer composable. Pulsa el botón y mira el mensaje en Logcat (etiqueta UD3)."
    ) {
        PrimerComposable()
    }
}

@Preview(showBackground = true)
@Composable
fun PrimerComposablePreview() {
    MiAppTheme {
        PrimerComposable()
    }
}
