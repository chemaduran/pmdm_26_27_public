package com.pmdm.ud3.estado.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.estado.ui.componentes.Muestra
import com.pmdm.ud3.estado.ui.componentes.PantallaDemo
import com.pmdm.ud3.estado.ui.componentes.TituloSeccion
import com.pmdm.ud3.estado.ui.theme.MiAppTheme

/**
 * Apartado 3.5 — Elevación de estado (State Hoisting).
 *
 *        Padre (stateful)       ← aquí vive el estado
 *          │  ▲
 *   estado │  │ eventos
 *  (baja)  ▼  │ (suben)
 *        Hijo (stateless)       ← solo muestra y avisa
 */
@Composable
fun PantallaHoisting() {
    PantallaDemo(titulo = "3.5 · State hoisting") {
        TituloSeccion(
            "❌ Antes: estado interno",
            "CampoNombreConEstadoInterno guarda su propio texto. Funciona, pero desde fuera " +
                "NO podemos saber qué se ha escrito (por eso no hay ningún \"Hola\" debajo)."
        )
        Muestra { Column(Modifier.padding(12.dp)) { CampoNombreConEstadoInterno() } }

        TituloSeccion(
            "✅ Después: estado elevado",
            "CampoNombre no tiene estado: recibe `nombre` y avisa con `onNombreChange`. " +
                "El padre (Formulario) guarda el estado y puede usarlo en otros sitios: " +
                "el saludo y el botón Guardar."
        )
        Muestra { Column(Modifier.padding(12.dp)) { Formulario() } }

        TituloSeccion(
            "Ejemplo completo: formulario con validación",
            "PantallaRegistro (con estado) + FormularioRegistro (sin estado). " +
                "El botón solo se activa si nombre no está vacío, el email tiene @ y la contraseña ≥ 6."
        )
        PantallaRegistro()
    }
}

// ---------- Antes ----------

/** En los apuntes se llama CampoNombre; lo renombramos para poder tener las dos versiones. */
@Composable
fun CampoNombreConEstadoInterno() {
    var nombre by remember { mutableStateOf("") }  // Estado interno

    OutlinedTextField(
        value = nombre,
        onValueChange = { nombre = it },
        label = { Text("Nombre") },
        modifier = Modifier.fillMaxWidth()
    )
}
// ❌ ¿Cómo accedo al valor de 'nombre' desde fuera? No puedo.

// ---------- Después ----------

@Composable
fun CampoNombre(
    nombre: String,                  // Estado: viene de arriba
    onNombreChange: (String) -> Unit // Evento: sube hacia arriba
) {
    OutlinedTextField(
        value = nombre,
        onValueChange = onNombreChange,
        label = { Text("Nombre") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Formulario() {
    var nombre by remember { mutableStateOf("") }    // El estado vive aquí
    var guardado by remember { mutableStateOf<String?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CampoNombre(
            nombre = nombre,
            onNombreChange = { nombre = it }
        )

        // Ahora puedo usar 'nombre' en cualquier otro sitio
        if (nombre.isNotBlank()) {
            Text("Hola, $nombre 👋")
        }

        Button(
            onClick = { guardado = nombre },
            enabled = nombre.isNotBlank()
        ) {
            Text("Guardar")
        }
        guardado?.let { Text("Guardado: \"$it\"", style = MaterialTheme.typography.labelMedium) }
    }
}

// ---------- Ejemplo completo ----------

@Composable
fun PantallaRegistro() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var mensaje by rememberSaveable { mutableStateOf<String?>(null) }

    val esFormularioValido = nombre.isNotBlank()
        && email.contains("@")
        && password.length >= 6

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FormularioRegistro(
            nombre = nombre,
            onNombreChange = { nombre = it },
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            esValido = esFormularioValido,
            onRegistrarse = {
                // Lógica de registro (aquí solo mostramos un mensaje)
                mensaje = "Usuario $nombre registrado con el email $email"
            }
        )
        mensaje?.let {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text("✅ $it", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun FormularioRegistro(
    nombre: String,
    onNombreChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    esValido: Boolean,
    onRegistrarse: () -> Unit
) {
    // En los apuntes es fillMaxSize(); aquí fillMaxWidth() porque va dentro de una pantalla con scroll
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registro", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            isError = email.isNotEmpty() && !email.contains("@"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            supportingText = { Text("Mínimo 6 caracteres") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onRegistrarse,
            enabled = esValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}

/**
 * Ventaja del hoisting: como FormularioRegistro no tiene estado, podemos
 * previsualizarlo en CUALQUIER situación pasándole los valores que queramos.
 */
@Preview(showBackground = true, name = "Formulario válido")
@Composable
fun FormularioRegistroValidoPreview() {
    MiAppTheme {
        FormularioRegistro(
            nombre = "Ana", onNombreChange = {},
            email = "ana@correo.es", onEmailChange = {},
            password = "123456", onPasswordChange = {},
            esValido = true, onRegistrarse = {}
        )
    }
}

@Preview(showBackground = true, name = "Email con error")
@Composable
fun FormularioRegistroErrorPreview() {
    MiAppTheme {
        FormularioRegistro(
            nombre = "Ana", onNombreChange = {},
            email = "ana-sin-arroba", onEmailChange = {},
            password = "", onPasswordChange = {},
            esValido = false, onRegistrarse = {}
        )
    }
}
