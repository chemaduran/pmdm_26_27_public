package com.pmdm.ud3.estado.peliculas

/**
 * Apartado 3.7 — Patrón UiState con sealed interface.
 *
 * Una pantalla que carga datos SIEMPRE está en uno (y solo uno) de estos tres estados.
 * Al ser `sealed`, el `when` de la pantalla obliga a tratar los tres casos:
 * si añadieras un cuarto estado, el código no compilaría hasta que lo pintaras.
 */
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val mensaje: String) : UiState<Nothing>
}

/** Modelo sencillo para este ejemplo. */
data class Pelicula(val id: Int, val titulo: String, val anio: Int)
