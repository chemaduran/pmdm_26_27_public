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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
 * Apartados 3.3 (rememberSaveable) y 3.4 (estado derivado).
 */
@Composable
fun PantallaSaveableDerivado() {
    PantallaDemo(titulo = "3.3 – 3.4 · Saveable y derivado") {
        TituloSeccion(
            "remember vs. rememberSaveable",
            "Pulsa los dos botones varias veces y GIRA EL MÓVIL (en el emulador, el botón de " +
                "rotar). El de la izquierda vuelve a 0; el de la derecha se conserva."
        )
        Muestra { ComparativaRotacion() }

        TituloSeccion(
            "FormularioRegistro con rememberSaveable",
            "Escribe tu nombre y email y gira el móvil: no pierdes lo escrito."
        )
        FormularioSaveable()

        TituloSeccion(
            "derivedStateOf: ListaFiltrada",
            "La lista filtrada se calcula a partir de la búsqueda. No la guardamos como otro estado: " +
                "se DERIVA del estado `busqueda`."
        )
        ListaFiltrada()
    }
}

@Composable
fun ComparativaRotacion() {
    var conRemember by remember { mutableIntStateOf(0) }
    var conSaveable by rememberSaveable { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("remember", style = MaterialTheme.typography.labelLarge)
            Text("$conRemember", style = MaterialTheme.typography.displaySmall)
            Button(onClick = { conRemember++ }) { Text("+1") }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("rememberSaveable", style = MaterialTheme.typography.labelLarge)
            Text("$conSaveable", style = MaterialTheme.typography.displaySmall)
            Button(onClick = { conSaveable++ }) { Text("+1") }
        }
    }
}

/**
 * El `FormularioRegistro` del apartado 3.3. Lo llamamos FormularioSaveable para no
 * confundirlo con el FormularioRegistro (sin estado) del apartado 3.5.
 */
@Composable
fun FormularioSaveable() {
    // Se mantiene al rotar la pantalla
    var nombre by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ListaFiltrada() {
    var busqueda by remember { mutableStateOf("") }
    val elementos = remember { listOf("Kotlin", "Java", "Python", "JavaScript", "Rust") }

    // Estado derivado: se recalcula automáticamente cuando cambia 'busqueda'
    val elementosFiltrados by remember(busqueda) {
        derivedStateOf {
            if (busqueda.isEmpty()) elementos
            else elementos.filter { it.contains(busqueda, ignoreCase = true) }
        }
    }

    Column {
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar (prueba con \"java\" o \"t\")") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("${elementosFiltrados.size} resultados", style = MaterialTheme.typography.labelLarge)
        elementosFiltrados.forEach { Text("• $it") }
    }
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun PantallaSaveableDerivadoPreview() {
    MiAppTheme {
        PantallaSaveableDerivado()
    }
}
