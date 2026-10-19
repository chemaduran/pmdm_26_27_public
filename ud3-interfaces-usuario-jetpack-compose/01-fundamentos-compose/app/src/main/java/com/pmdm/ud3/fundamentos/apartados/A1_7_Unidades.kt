package com.pmdm.ud3.fundamentos.apartados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.ud3.fundamentos.ui.componentes.TarjetaApartado
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Apartado 1.7 — Unidades de medida: dp y sp.
 *
 * EXPERIMENTO: con la app abierta, ve a Ajustes del móvil > Pantalla > Tamaño de fuente,
 * súbelo al máximo y vuelve a la app:
 *  - El texto en sp CRECE (respeta la accesibilidad del usuario).
 *  - La caja en dp NO cambia (las dimensiones no dependen de la fuente).
 */
@Composable
fun EjemploUnidades() {
    // LocalDensity nos da información de la pantalla actual
    val densidad = LocalDensity.current

    Column {
        Text(
            text = "Texto grande",
            fontSize = 24.sp,                          // sp para texto
            modifier = Modifier.padding(16.dp)         // dp para dimensiones
        )
        Text("Caja de 100dp × 24dp:", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Box100x24()
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "En este dispositivo:\n" +
                "  1 dp = ${densidad.density} px\n" +
                "  escala de fuente del usuario = ${densidad.fontScale}\n" +
                "  → 1 sp = ${densidad.density * densidad.fontScale} px",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun Box100x24() {
    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 24.dp)
            .background(MaterialTheme.colorScheme.tertiary)
    )
}

@Composable
fun ApartadoUnidades() {
    TarjetaApartado(
        numero = "1.7",
        titulo = "Unidades: dp y sp",
        explicacion = "dp para dimensiones, sp para texto. Cambia el tamaño de fuente en los " +
            "Ajustes del móvil y vuelve: el texto crece, la caja no."
    ) {
        EjemploUnidades()
    }
}

// Preview simulando un usuario con la fuente al 150 %
@Preview(showBackground = true, fontScale = 1.5f, name = "Fuente al 150 %")
@Preview(showBackground = true, name = "Fuente normal")
@Composable
fun EjemploUnidadesPreview() {
    MiAppTheme {
        EjemploUnidades()
    }
}
