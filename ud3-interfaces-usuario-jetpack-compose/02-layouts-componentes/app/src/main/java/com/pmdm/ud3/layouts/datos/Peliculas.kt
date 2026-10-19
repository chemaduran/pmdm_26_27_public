package com.pmdm.ud3.layouts.datos

/**
 * Modelo de datos del apartado 2.5.
 * Es una data class normal de Kotlin (UD2): Compose no necesita nada especial.
 */
data class Pelicula(
    val id: Int,
    val titulo: String,
    val director: String,
    val anio: Int,
    val genero: String,
    val puntuacion: Float
)

/** Datos de ejemplo (en una app real vendrían de una base de datos o de internet). */
val peliculasDeEjemplo = listOf(
    Pelicula(1, "Interstellar", "Christopher Nolan", 2014, "Ciencia ficción", 8.7f),
    Pelicula(2, "Parásitos", "Bong Joon-ho", 2019, "Drama", 8.5f),
    Pelicula(3, "El Padrino", "Francis Ford Coppola", 1972, "Drama", 9.2f),
    Pelicula(4, "Blade Runner 2049", "Denis Villeneuve", 2017, "Ciencia ficción", 8.0f),
    Pelicula(5, "El viaje de Chihiro", "Hayao Miyazaki", 2001, "Animación", 8.6f),
    Pelicula(6, "Coco", "Lee Unkrich", 2017, "Animación", 8.4f),
    Pelicula(7, "Origen", "Christopher Nolan", 2010, "Ciencia ficción", 8.8f),
    Pelicula(8, "El laberinto del fauno", "Guillermo del Toro", 2006, "Fantasía", 8.2f),
    Pelicula(9, "Amélie", "Jean-Pierre Jeunet", 2001, "Comedia", 8.3f),
    Pelicula(10, "Toy Story", "John Lasseter", 1995, "Animación", 8.3f)
)

/** Películas que el FAB "Añadir" irá incorporando a la lista. */
val peliculasParaAnadir = peliculasDeEjemplo + listOf(
    Pelicula(11, "Mar adentro", "Alejandro Amenábar", 2004, "Drama", 8.0f),
    Pelicula(12, "Matrix", "Lana y Lilly Wachowski", 1999, "Ciencia ficción", 8.7f),
    Pelicula(13, "Up", "Pete Docter", 2009, "Animación", 8.3f)
)

/** Las películas agrupadas por género: Map<String, List<String>> para el apartado "secciones". */
val peliculasPorGenero: Map<String, List<String>> =
    peliculasDeEjemplo.groupBy({ it.genero }, { it.titulo })

/** 100 contactos generados para comprobar que LazyColumn solo dibuja los visibles. */
val contactosDeEjemplo = List(100) { i -> "Contacto ${i + 1}" }

/** 30 "imágenes" para la galería en cuadrícula. */
val imagenesDeEjemplo = List(30) { i -> "🖼️ ${i + 1}" }
