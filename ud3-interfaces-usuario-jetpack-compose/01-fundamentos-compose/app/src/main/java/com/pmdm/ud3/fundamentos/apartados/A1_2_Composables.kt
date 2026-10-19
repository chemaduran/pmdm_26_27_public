package com.pmdm.ud3.fundamentos.apartados

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pmdm.ud3.fundamentos.ui.componentes.TarjetaApartado
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/*
 * Apartados 1.2 y 1.3 (Composición) — Funciones @Composable y árbol de UI.
 *
 * Aquí está el árbol de los apuntes construido de verdad:
 *
 *   PantallaInicio()
 *   ├── Column
 *   │   ├── Cabecera()
 *   │   │   └── Text("Mi Aplicación")
 *   │   ├── ListaDeElementos()
 *   │   │   ├── ElementoLista("Item 1")
 *   │   │   ├── ElementoLista("Item 2")
 *   │   │   └── ElementoLista("Item 3")
 *   │   └── PieDeSeccion()
 *   │       └── Text("3 elementos")
 *
 * Reglas que cumplen todas estas funciones:
 *  - Llevan @Composable y se nombran en PascalCase (como las clases).
 *  - No devuelven nada (Unit): EMITEN UI.
 *  - Solo se pueden llamar desde otra función @Composable.
 *    Prueba a llamar a Cabecera() desde una función normal: ¡no compila!
 *
 * Truco: ejecuta la app y abre Tools > Layout Inspector. Verás este mismo árbol.
 */

private val elementos = listOf("Item 1", "Item 2", "Item 3")

@Composable
fun PantallaInicio() {
    Column {
        Cabecera()
        ListaDeElementos()
        PieDeSeccion()
    }
}

@Composable
fun Cabecera() {
    Text(
        text = "Mi Aplicación",
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun ListaDeElementos() {
    // Un bucle normal de Kotlin dentro de un composable: emite un ElementoLista por cada item
    elementos.forEach { texto ->
        ElementoLista(texto)
    }
}

@Composable
fun ElementoLista(texto: String) {
    Text(text = "• $texto", style = MaterialTheme.typography.bodyLarge)
}

@Composable
fun PieDeSeccion() {
    Text(
        text = "${elementos.size} elementos",
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
fun ApartadoComposables() {
    TarjetaApartado(
        numero = "1.2",
        titulo = "Funciones @Composable",
        explicacion = "PantallaInicio() se construye combinando composables más pequeños: " +
            "Cabecera(), ListaDeElementos() y PieDeSeccion(). Es el árbol de la sección 1.3."
    ) {
        PantallaInicio()
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    MiAppTheme {
        PantallaInicio()
    }
}
