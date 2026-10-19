package com.pmdm.ud3.fundamentos.apartados

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.fundamentos.ui.componentes.TarjetaApartado
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Apartado 1.6 — Modificadores.
 *
 * Tres demostraciones:
 *  1. La cadena de modificadores de los apuntes (con un clickable que cuenta pulsaciones).
 *  2. "El orden importa": padding→background frente a background→padding, intercambiable.
 *  3. Una pequeña galería con los modificadores de la tabla.
 */

/** 1. El ejemplo de los apuntes, tal cual. Pulsa el texto: el clickable funciona. */
@Composable
fun CadenaDeModificadores() {
    var pulsaciones by remember { mutableIntStateOf(0) }

    Text(
        text = "Hola (pulsado $pulsaciones veces)",
        modifier = Modifier
            .fillMaxWidth()           // Ocupa todo el ancho
            .padding(16.dp)           // Padding exterior de 16dp
            .background(Color.LightGray, RoundedCornerShape(8.dp))  // Fondo gris redondeado
            .padding(12.dp)           // Padding interior de 12dp
            .clickable { pulsaciones++ }  // Hace el texto clickable
    )
}

/**
 * 2. El orden importa.
 * El borde gris (que se aplica PRIMERO) marca el tamaño total del elemento,
 * para que veas dónde cae el padding en cada caso.
 */
@Composable
fun OrdenDeModificadores() {
    var paddingPrimero by remember { mutableStateOf(true) }

    val modificador = if (paddingPrimero) {
        // Padding FUERA del fondo → se ve espacio, luego fondo rojo
        Modifier
            .border(1.dp, Color.Gray)
            .padding(16.dp)
            .background(Color.Red)
    } else {
        // Padding DENTRO del fondo → el fondo rojo incluye el padding
        Modifier
            .border(1.dp, Color.Gray)
            .background(Color.Red)
            .padding(16.dp)
    }

    Column {
        Text(
            text = if (paddingPrimero) "Modifier.padding(16.dp).background(Red)"
                   else "Modifier.background(Red).padding(16.dp)",
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Texto", color = Color.White, modifier = modificador)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = { paddingPrimero = !paddingPrimero }) {
            Text("Intercambiar el orden")
        }
    }
}

/** 3. Galería de modificadores de la tabla "Modificadores más comunes". */
@Composable
fun GaleriaDeModificadores() {
    val color = MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EjemploModificador("size") {
            Box(Modifier.size(40.dp).background(color))
        }
        EjemploModificador("border") {
            Box(Modifier.size(40.dp).border(3.dp, color, RoundedCornerShape(8.dp)))
        }
        EjemploModificador("clip") {
            Box(Modifier.size(40.dp).clip(CircleShape).background(color))
        }
        EjemploModificador("alpha") {
            Box(Modifier.size(40.dp).alpha(0.3f).background(color))
        }
        EjemploModificador("offset") {
            Box(Modifier.size(40.dp).offset(x = 10.dp, y = 10.dp).background(color))
        }
        EjemploModificador("shadow") {
            Box(Modifier.size(40.dp).shadow(8.dp, RoundedCornerShape(8.dp)).background(color))
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    // weight: reparte el espacio en proporción (lo verás a fondo en la lección 2)
    Text("weight(1f) | weight(2f)", style = MaterialTheme.typography.labelSmall)
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(Modifier.weight(1f).height(24.dp).background(Color(0xFFE57373)))
        Box(Modifier.weight(2f).height(24.dp).background(Color(0xFF64B5F6)))
    }
}

@Composable
private fun EjemploModificador(nombre: String, ejemplo: @Composable () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.TopStart) {
            ejemplo()
        }
        Text(nombre, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun ApartadoModificadores() {
    TarjetaApartado(
        numero = "1.6",
        titulo = "Modificadores",
        explicacion = "Los modificadores se encadenan y se aplican como capas, de fuera hacia dentro. " +
            "Pulsa \"Intercambiar el orden\" y fíjate en dónde queda el espacio."
    ) {
        Column {
            CadenaDeModificadores()
            Spacer(modifier = Modifier.height(16.dp))
            OrdenDeModificadores()
            Spacer(modifier = Modifier.height(16.dp))
            GaleriaDeModificadores()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApartadoModificadoresPreview() {
    MiAppTheme {
        ApartadoModificadores()
    }
}
