package com.pmdm.ud3.fundamentos.apartados

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.fundamentos.ui.componentes.TarjetaApartado
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Apartado 1.1 — Paradigma declarativo.
 *
 * Este es el composable `Saludo` de los apuntes, tal cual.
 * DESCRIBE qué se ve ("un texto rojo que dice Hola, <nombre>"),
 * no CÓMO cambiarlo (no hay findViewById, ni setText, ni setTextColor).
 */
@Composable
fun Saludo(nombre: String) {
    Text(
        text = "Hola, $nombre",
        color = Color.Red
    )
}

private val nombres = listOf("Mundo", "Ana", "Luis", "Compose")

@Composable
fun ApartadoDeclarativo() {
    // Estado: el índice del nombre que mostramos (remember se explica en 1.3 y en la lección 3)
    var indice by remember { mutableIntStateOf(0) }

    TarjetaApartado(
        numero = "1.1",
        titulo = "Declarativo vs. imperativo",
        explicacion = "La UI es una función del estado. Al pulsar el botón NO modificamos el " +
            "texto: cambiamos el estado (el nombre) y Compose vuelve a llamar a Saludo()."
    ) {
        Column {
            // UI = f(estado): le pasamos el nombre actual y Saludo "describe" el resultado
            Saludo(nombre = nombres[indice])
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { indice = (indice + 1) % nombres.size }) {
                Text("Cambiar nombre")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "En el View system (imperativo) habríamos tenido que buscar la vista " +
                    "y mutarla a mano:\ntvSaludo.text = \"Hola, Ana\"",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaludoPreview() {
    MiAppTheme {
        Saludo(nombre = "Mundo")
    }
}
