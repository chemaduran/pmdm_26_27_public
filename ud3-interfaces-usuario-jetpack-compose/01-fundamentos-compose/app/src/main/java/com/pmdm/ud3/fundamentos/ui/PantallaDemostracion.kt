package com.pmdm.ud3.fundamentos.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.fundamentos.apartados.ApartadoComposables
import com.pmdm.ud3.fundamentos.apartados.ApartadoDeclarativo
import com.pmdm.ud3.fundamentos.apartados.ApartadoEstructuraMinima
import com.pmdm.ud3.fundamentos.apartados.ApartadoModificadores
import com.pmdm.ud3.fundamentos.apartados.ApartadoPreview
import com.pmdm.ud3.fundamentos.apartados.ApartadoRecomposicion
import com.pmdm.ud3.fundamentos.apartados.ApartadoTarjetaPerfil
import com.pmdm.ud3.fundamentos.apartados.ApartadoUnidades
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Pantalla principal: una columna con scroll que muestra, en orden,
 * un ejemplo funcionando de cada apartado de la lección 1.
 *
 * Cada ApartadoXxx() está en su propio fichero dentro del paquete "apartados".
 * Ábrelos en modo Split (código + preview) mientras lees los apuntes.
 */
@Composable
fun PantallaDemostracion() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()                    // Evita que el contenido quede bajo la barra de estado
            .verticalScroll(rememberScrollState())  // Modificador de scroll (tabla del apartado 1.6)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Lección 1 · Fundamentos de Compose",
            style = MaterialTheme.typography.headlineMedium
        )
        ApartadoDeclarativo()       // 1.1
        ApartadoComposables()       // 1.2
        ApartadoRecomposicion()     // 1.3
        ApartadoEstructuraMinima()  // 1.4
        ApartadoPreview()           // 1.5
        ApartadoModificadores()     // 1.6
        ApartadoUnidades()          // 1.7
        ApartadoTarjetaPerfil()     // 1.8
    }
}

@Preview(showBackground = true, heightDp = 2000)
@Composable
fun PantallaDemostracionPreview() {
    MiAppTheme {
        PantallaDemostracion()
    }
}
