package com.pmdm.ud3.navegacion.navegacion

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

/**
 * Apartado 4.2, paso 1 — Definir las rutas.
 *
 * ENFOQUE CLÁSICO CON STRINGS (el que usamos en casi toda la app).
 * Una ruta es como una URL: "detalle/42", "lista?filtro=Drama"...
 */
object Rutas {
    const val LOGIN = "login"
    const val INICIO = "inicio"
    const val LISTA = "lista?filtro={filtro}"      // argumento OPCIONAL (query parameter)
    const val DETALLE = "detalle/{peliculaId}"     // argumento OBLIGATORIO
    const val AGREGAR = "agregar"
    const val PERFIL = "perfil"

    /** Construye la ruta real de la lista, con o sin filtro. */
    fun lista(filtro: String? = null): String =
        if (filtro == null) "lista"
        else "lista?filtro=${Uri.encode(filtro)}"   // encode: "Ciencia ficción" lleva espacio

    /** Construye la ruta real del detalle: detalle(42) → "detalle/42". */
    fun detalle(id: Int) = "detalle/$id"
}

/**
 * ENFOQUE MODERNO TYPE-SAFE (Navigation 2.8+).
 * La ruta es una clase @Serializable y sus propiedades son los argumentos.
 * Ventaja: si te equivocas de tipo o te falta un argumento, NO COMPILA.
 *
 * Lo usamos solo para la pantalla de Créditos (menú lateral), para que puedas
 * comparar los dos enfoques en el mismo proyecto.
 */
@Serializable
data class Creditos(val autor: String, val curso: String)

/**
 * Apartado 4.5 — Las secciones de la barra de navegación inferior.
 * `ruta` es la ruta a la que navega cada botón.
 */
enum class SeccionPrincipal(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
) {
    INICIO(Rutas.INICIO, "Inicio", Icons.Default.Home),
    PELICULAS(Rutas.lista(), "Películas", Icons.Default.Movie),
    PERFIL(Rutas.PERFIL, "Perfil", Icons.Default.Person)
}
