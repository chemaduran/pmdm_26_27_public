package com.pmdm.ud3.fundamentos.apartados

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.fundamentos.ui.componentes.TarjetaApartado
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Apartado 1.3 — Recomposición.
 *
 * Es el `Contador` de los apuntes, pero cambiando cada Text() por
 * TextoQueCuentaEjecuciones(), que muestra cuántas veces se ha ejecutado.
 *
 * Pulsa varias veces el botón y observa:
 *  - "Has pulsado X veces"     → su contador SUBE (depende de `cuenta`, se recompone).
 *  - "Pulsar"                  → se queda en 1 (no depende de `cuenta`, Compose lo SALTA).
 *  - "Este texto nunca cambia" → se queda en 1 (sus entradas no han cambiado).
 *
 * ¡Eso es la recomposición inteligente!
 */
@Composable
fun Contador() {
    var cuenta by remember { mutableIntStateOf(0) }

    Column {
        TextoQueCuentaEjecuciones("Has pulsado $cuenta veces")  // ← Se recompone al cambiar 'cuenta'
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { cuenta++ }) {
            TextoQueCuentaEjecuciones("Pulsar")                  // ← NO se recompone
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextoFijo()                                              // ← NO se recompone
    }
}

@Composable
fun TextoFijo() {
    TextoQueCuentaEjecuciones("Este texto nunca cambia")
}

/**
 * Herramienta de DEPURACIÓN (no la uses en una app real): cuenta cuántas veces
 * se ejecuta este composable.
 *
 * - `remember { Contenedor() }` guarda un objeto que sobrevive entre recomposiciones.
 * - Incrementar su valor durante la composición es un "efecto secundario", justo lo que
 *   la tabla de reglas de 1.2 dice que NO hay que hacer; aquí lo hacemos solo para verlo.
 * - SideEffect { } es la API correcta para efectos: se ejecuta tras cada composición
 *   terminada. La usamos para escribir en Logcat (filtra por la etiqueta "UD3").
 */
@Composable
fun TextoQueCuentaEjecuciones(texto: String) {
    val ejecuciones = remember { Contenedor() }
    ejecuciones.valor++

    SideEffect {
        Log.d("UD3", "Recompuesto \"$texto\" → ${ejecuciones.valor} veces")
    }

    Text(
        text = "$texto   [ejecutado ${ejecuciones.valor}×]",
        style = MaterialTheme.typography.bodyLarge
    )
}

/** Objeto mutable normal (NO es estado de Compose: cambiarlo no provoca recomposición). */
class Contenedor(var valor: Int = 0)

@Composable
fun ApartadoRecomposicion() {
    TarjetaApartado(
        numero = "1.3",
        titulo = "Composición y recomposición",
        explicacion = "Pulsa el botón varias veces. Entre corchetes ves cuántas veces se ha " +
            "ejecutado cada composable: solo se re-ejecuta lo que depende del estado que cambia."
    ) {
        Contador()
    }
}

@Preview(showBackground = true)
@Composable
fun ContadorPreview() {
    MiAppTheme {
        Contador()
    }
}
