package com.pmdm.ud3.estado.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.estado.ui.componentes.Muestra
import com.pmdm.ud3.estado.ui.componentes.PantallaDemo
import com.pmdm.ud3.estado.ui.componentes.TituloSeccion
import com.pmdm.ud3.estado.ui.theme.MiAppTheme

/**
 * Apartados 3.1 y 3.2 — El problema de las variables normales, remember y mutableStateOf.
 */
@Composable
fun PantallaRemember() {
    PantallaDemo(titulo = "3.1 – 3.2 · remember") {
        TituloSeccion(
            "❌ ContadorRoto",
            "Usa una variable normal. Pulsa: el número NO cambia. La variable sí se incrementa, " +
                "pero Compose no se entera (no es estado observable) y, si recompusiera, " +
                "volvería a crearla con valor 0."
        )
        Muestra { Column(Modifier.padding(16.dp)) { ContadorRoto() } }

        TituloSeccion(
            "✅ Contador",
            "remember { mutableIntStateOf(0) }: Compose RECUERDA el valor y RECOMPONE al cambiar."
        )
        Muestra { Column(Modifier.padding(16.dp)) { Contador() } }

        TituloSeccion(
            "Tres formas de declarar estado",
            "Los tres campos hacen lo mismo; solo cambia la sintaxis. En el curso usamos la forma 1 (by)."
        )
        TresFormasDeDeclararEstado()

        TituloSeccion(
            "Experimento",
            "Sube el contador, cambia a otra pestaña y vuelve: se ha reiniciado a 0. " +
                "Al cambiar de pestaña este composable sale de la composición y remember lo olvida."
        )
    }
}

@Composable
fun ContadorRoto() {
    var cuenta = 0                  // ❌ Se reinicia en cada recomposición

    Button(onClick = { cuenta++ }) {
        Text("Cuenta: $cuenta")     // Siempre mostrará 0
    }
}

@Composable
fun Contador() {
    // remember { } → Compose guarda este valor entre recomposiciones
    // mutableIntStateOf(0) → Valor observable que dispara recomposición
    var cuenta by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cuenta: $cuenta",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { cuenta-- }) { Text("-1") }
            Button(onClick = { cuenta++ }) { Text("+1") }
            OutlinedButton(onClick = { cuenta = 0 }) { Text("Reset") }
        }
    }
}

@Composable
fun TresFormasDeDeclararEstado() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Forma 1: by delegation (RECOMENDADA — la más limpia)
        var nombre1 by remember { mutableStateOf("") }
        OutlinedTextField(
            value = nombre1,                    // se lee directamente
            onValueChange = { nombre1 = it },   // se asigna directamente
            label = { Text("Forma 1 · var nombre by remember { … }") },
            modifier = Modifier.fillMaxWidth()
        )

        // Forma 2: asignación directa (requiere .value)
        val nombre2 = remember { mutableStateOf("") }
        OutlinedTextField(
            value = nombre2.value,
            onValueChange = { nombre2.value = it },
            label = { Text("Forma 2 · val nombre = remember { … } + .value") },
            modifier = Modifier.fillMaxWidth()
        )

        // Forma 3: desestructuración
        val (nombre3, setNombre3) = remember { mutableStateOf("") }
        OutlinedTextField(
            value = nombre3,
            onValueChange = { setNombre3(it) },
            label = { Text("Forma 3 · val (nombre, setNombre) = …") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun PantallaRememberPreview() {
    MiAppTheme {
        PantallaRemember()
    }
}
