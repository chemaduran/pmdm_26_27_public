package com.pmdm.ud3.eventos.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.eventos.ui.componentes.PantallaDemo
import com.pmdm.ud3.eventos.ui.theme.MiAppTheme

/**
 * Apartado 6.6 — Teclado y gestión del foco.
 *
 * EXPERIMENTOS (con el teclado en pantalla del emulador/móvil):
 *  1. En el formulario, pulsa el botón "→" del teclado: el foco salta al siguiente campo.
 *  2. En el último, pulsa "✓": el teclado se oculta (clearFocus).
 *  3. En los campos de abajo, fíjate en que CADA UNO abre un teclado distinto.
 */
@Composable
fun PantallaTeclado(onVolver: () -> Unit) {
    PantallaDemo(titulo = "6.6 · Teclado y foco", onVolver = onVolver) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .imePadding()          // deja hueco para el teclado y así el contenido no queda tapado
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("FormularioConFoco", style = MaterialTheme.typography.titleMedium)
            FormularioConFoco()
            HorizontalDivider()
            Text("Tipos de teclado (KeyboardType)", style = MaterialTheme.typography.titleMedium)
            TiposDeTeclado()
        }
    }
}

/** El FormularioConFoco de los apuntes + un mensaje al pulsar "Enviar". */
@Composable
fun FormularioConFoco() {
    val focusManager = LocalFocusManager.current
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var enviado by remember { mutableStateOf<String?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words   // Cada Palabra En Mayúscula
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            label = { Text("Mensaje") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }   // Oculta el teclado
            ),
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                focusManager.clearFocus()    // Oculta el teclado al pulsar el botón
                enviado = "Enviado: $nombre <$email> · \"$mensaje\""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        enviado?.let {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Text(it, modifier = Modifier.padding(12.dp))
            }
        }
    }
}

/** Un campo por cada fila de la tabla "Tipos de teclado". */
@Composable
fun TiposDeTeclado() {
    val tipos = listOf(
        "Text" to KeyboardType.Text,
        "Number" to KeyboardType.Number,
        "Phone" to KeyboardType.Phone,
        "Email" to KeyboardType.Email,
        "Password" to KeyboardType.Password,
        "Uri" to KeyboardType.Uri,
        "Decimal" to KeyboardType.Decimal
    )
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        tipos.forEachIndexed { indice, (nombre, tipo) ->
            var valor by remember { mutableStateOf("") }
            val esUltimo = indice == tipos.lastIndex
            OutlinedTextField(
                value = valor,
                onValueChange = { valor = it },
                label = { Text("KeyboardType.$nombre") },
                singleLine = true,
                visualTransformation = if (tipo == KeyboardType.Password) PasswordVisualTransformation()
                                       else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    keyboardType = tipo,
                    imeAction = if (esUltimo) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onDone = { focusManager.clearFocus() }
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun PantallaTecladoPreview() {
    MiAppTheme { PantallaTeclado(onVolver = {}) }
}
