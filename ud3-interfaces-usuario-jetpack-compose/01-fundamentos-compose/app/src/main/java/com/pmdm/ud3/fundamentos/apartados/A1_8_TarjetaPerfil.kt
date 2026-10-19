package com.pmdm.ud3.fundamentos.apartados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.ud3.fundamentos.ui.componentes.TarjetaApartado
import com.pmdm.ud3.fundamentos.ui.theme.MiAppTheme

/**
 * Apartado 1.8 — Ejemplo completo: Tarjeta de perfil.
 *
 * Código idéntico al de los apuntes. Lo interesante: es REUTILIZABLE.
 * Escribimos TarjetaPerfil una vez y la usamos tres veces con datos distintos,
 * igual que llamarías a una función normal con distintos argumentos.
 */
@Composable
fun TarjetaPerfil(
    nombre: String,
    rol: String,
    avatarUrl: String? = null   // Aún no lo usamos: cargar imágenes de red se ve más adelante
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar (placeholder circular)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = nombre.first().uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Datos
            Column {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = rol,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ApartadoTarjetaPerfil() {
    TarjetaApartado(
        numero = "1.8",
        titulo = "Ejemplo completo: tarjeta de perfil",
        explicacion = "Un mismo composable reutilizado con distintos datos. Combina Card, Row, " +
            "Box, Column, Spacer y los modificadores clip, size y background."
    ) {
        Column {
            TarjetaPerfil(nombre = "Ana García", rol = "Desarrolladora Android")
            TarjetaPerfil(nombre = "Luis Pérez", rol = "Diseñador UX")
            TarjetaPerfil(nombre = "Marta Ruiz", rol = "QA / Testing")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaPerfilPreview() {
    MiAppTheme {
        TarjetaPerfil(
            nombre = "Ana García",
            rol = "Desarrolladora Android"
        )
    }
}
