package com.pmdm.ud3.estado.peliculas

import kotlinx.coroutines.delay

/**
 * Repositorio FALSO: simula una llamada a internet.
 *
 * En los apuntes aparece `repositorio.obtenerPeliculas()` sin más detalle.
 * Aquí lo implementamos con un `delay` (corrutinas, UD2) para que se vea el estado
 * Loading, y con la opción de fallar para que se vea el estado Error.
 * En la UD5 lo sustituirás por una llamada real con Retrofit.
 */
class RepositorioPeliculas {

    /** Si es true, la próxima llamada lanzará una excepción (como si no hubiera conexión). */
    var simularFallo: Boolean = false

    suspend fun obtenerPeliculas(): List<Pelicula> {
        delay(2_000)  // 2 segundos "esperando al servidor"
        if (simularFallo) {
            throw Exception("No se pudo conectar con el servidor")
        }
        return listOf(
            Pelicula(1, "Interstellar", 2014),
            Pelicula(2, "Parásitos", 2019),
            Pelicula(3, "El Padrino", 1972),
            Pelicula(4, "El viaje de Chihiro", 2001),
            Pelicula(5, "Origen", 2010)
        )
    }
}
