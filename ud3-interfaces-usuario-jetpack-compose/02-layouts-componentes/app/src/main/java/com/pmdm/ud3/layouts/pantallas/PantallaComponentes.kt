package com.pmdm.ud3.layouts.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.ud3.layouts.ui.componentes.TituloSeccion
import com.pmdm.ud3.layouts.ui.theme.MiAppTheme

/**
 * Apartado 2.3 — Componentes Material 3.
 *
 * Un "catálogo" con todos los componentes de los apuntes funcionando.
 * La TopAppBar de esta pantalla es MiTopAppBar() y el FAB abre el AlertDialog.
 */
@Composable
fun PantallaComponentes() {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var resultadoDialogo by remember { mutableStateOf("Aún no has abierto el diálogo") }
    var ultimoBoton by remember { mutableStateOf("ninguno") }

    Scaffold(
        topBar = { MiTopAppBar() },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { mostrarDialogo = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nueva tarea") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            TituloSeccion("Text", "Estilos predefinidos del tema y un texto personalizado.")
            EjemploTextos()

            TituloSeccion("Button", "Último botón pulsado: $ultimoBoton")
            EjemploBotones(onPulsado = { ultimoBoton = it })

            TituloSeccion("TextField", "Los dos campos comparten el mismo estado: escribe en uno y cambia el otro.")
            CampoDeTexto()

            TituloSeccion("Card", "Card normal (de los apuntes), ElevatedCard y OutlinedCard.")
            TarjetaPelicula(titulo = "Interstellar", anio = 2014, genero = "Ciencia ficción")
            Spacer(Modifier.padding(4.dp))
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Text("ElevatedCard → con sombra", modifier = Modifier.padding(16.dp))
            }
            Spacer(Modifier.padding(4.dp))
            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Text("OutlinedCard → con borde", modifier = Modifier.padding(16.dp))
            }

            TituloSeccion("FloatingActionButton", "El FAB extendido real está abajo a la derecha. Pulsa cualquiera.")
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FloatingActionButton(
                    onClick = { mostrarDialogo = true },
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Crear nuevo")
                }
                ExtendedFloatingActionButton(
                    onClick = { mostrarDialogo = true },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("Nueva tarea") }
                )
            }

            TituloSeccion("AlertDialog", resultadoDialogo)
            OutlinedButton(onClick = { mostrarDialogo = true }) {
                Text("Eliminar elemento…")
            }

            TituloSeccion("Otros componentes", "Mueve el Slider: la barra de progreso usa su mismo valor.")
            OtrosComponentes()

            Spacer(Modifier.padding(48.dp))  // Hueco para que el FAB no tape lo último
        }
    }

    // El diálogo se declara fuera del Scaffold. Solo aparece cuando mostrarDialogo == true
    DialogoConfirmacion(
        mostrar = mostrarDialogo,
        onConfirmar = {
            resultadoDialogo = "Has pulsado ELIMINAR"
            mostrarDialogo = false
        },
        onCancelar = {
            resultadoDialogo = "Has pulsado CANCELAR (o has tocado fuera)"
            mostrarDialogo = false
        }
    )
}

// ---------- Ejemplos de los apuntes ----------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiTopAppBar() {
    TopAppBar(
        title = { Text("2.3 · Componentes") },
        navigationIcon = {
            // En la lección 4 este botón navegará hacia atrás de verdad
            IconButton(onClick = { /* navegar atrás */ }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
            }
        },
        actions = {
            IconButton(onClick = { /* buscar */ }) {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
            IconButton(onClick = { /* menú */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun EjemploTextos() {
    Column {
        // Estilos de texto predefinidos
        Text("Display Large", style = MaterialTheme.typography.displayLarge)
        Text("Headline Medium", style = MaterialTheme.typography.headlineMedium)
        Text("Title Small", style = MaterialTheme.typography.titleSmall)
        Text("Body Large", style = MaterialTheme.typography.bodyLarge)
        Text("Label Medium", style = MaterialTheme.typography.labelMedium)

        // Personalizado: con maxLines = 2 y Ellipsis, lo que no cabe se corta con "…"
        Text(
            text = "Texto personalizado, en negrita, centrado y tan largo que no cabe en dos " +
                "líneas, así que se corta con puntos suspensivos al final del todo",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun EjemploBotones(onPulsado: (String) -> Unit) {
    // FlowRow: como un Row, pero salta de línea cuando no caben más elementos
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Botón principal (filled)
        Button(onClick = { onPulsado("Button") }) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Añadir")
        }
        // Botón secundario (outlined)
        OutlinedButton(onClick = { onPulsado("OutlinedButton") }) {
            Text("Cancelar")
        }
        // Botón de texto
        TextButton(onClick = { onPulsado("TextButton") }) {
            Text("Más información")
        }
        // Botón con relleno de tono
        FilledTonalButton(onClick = { onPulsado("FilledTonalButton") }) {
            Text("Opción secundaria")
        }
        // Botón elevado
        ElevatedButton(onClick = { onPulsado("ElevatedButton") }) {
            Text("Elevado")
        }
        // Botón con icono
        IconButton(onClick = { onPulsado("IconButton") }) {
            Icon(Icons.Default.Favorite, contentDescription = "Favorito")
        }
    }
}

@Composable
fun CampoDeTexto() {
    var texto by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // TextField lleno (por defecto en Material 3)
        TextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Nombre") },
            placeholder = { Text("Introduce tu nombre") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            trailingIcon = {
                if (texto.isNotEmpty()) {
                    IconButton(onClick = { texto = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                    }
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // TextField con contorno: escribe algo sin "@" para ver el estado de error
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            isError = texto.isNotEmpty() && !texto.contains("@"),
            supportingText = {
                if (texto.isNotEmpty() && !texto.contains("@")) {
                    Text("Email no válido", color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TarjetaPelicula(titulo: String, anio: Int, genero: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("$anio", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(8.dp))
                AssistChip(
                    onClick = { },
                    label = { Text(genero) }
                )
            }
        }
    }
}

@Composable
fun DialogoConfirmacion(
    mostrar: Boolean,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    if (mostrar) {
        AlertDialog(
            onDismissRequest = onCancelar,
            icon = { Icon(Icons.Default.Warning, contentDescription = null) },
            title = { Text("¿Eliminar elemento?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = onConfirmar) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = onCancelar) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun OtrosComponentes() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Checkbox
        var checked by remember { mutableStateOf(false) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked, onCheckedChange = { checked = it })
            Text("Acepto los términos")
        }

        // Switch
        var activado by remember { mutableStateOf(false) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(checked = activado, onCheckedChange = { activado = it })
            Spacer(Modifier.width(8.dp))
            Text(if (activado) "Activado" else "Desactivado")
        }

        // Slider
        var valor by remember { mutableFloatStateOf(0.5f) }
        Text("Slider: ${(valor * 100).toInt()} %")
        Slider(value = valor, onValueChange = { valor = it })

        // LinearProgressIndicator conectado al Slider
        LinearProgressIndicator(progress = { valor }, modifier = Modifier.fillMaxWidth())

        // CircularProgressIndicator (indeterminado: gira sin parar)
        CircularProgressIndicator()

        // Divider
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

        // Chip: pulsa para activarlo/desactivarlo
        var filtroActivo by remember { mutableStateOf(true) }
        FilterChip(
            selected = filtroActivo,
            onClick = { filtroActivo = !filtroActivo },
            label = { Text(if (filtroActivo) "Filtro activo" else "Filtro inactivo") },
            leadingIcon = if (filtroActivo) {
                { Icon(Icons.Default.Check, contentDescription = null) }
            } else null
        )
    }
}

@Preview(showBackground = true, heightDp = 2200)
@Composable
fun PantallaComponentesPreview() {
    MiAppTheme {
        PantallaComponentes()
    }
}
