package com.pmdm.ud3.navegacion.datos

/**
 * Modelo del apartado 4.6. Le añadimos `genero` para poder demostrar
 * el argumento opcional "filtro" de la ruta de la lista.
 */
data class Pelicula(
    val id: Int,
    val titulo: String,
    val anio: Int,
    val sinopsis: String,
    val genero: String = "Otros"
)

val peliculasIniciales = listOf(
    Pelicula(1, "Interstellar", 2014, "Un grupo de exploradores viaja a través de un agujero de gusano...", "Ciencia ficción"),
    Pelicula(2, "Parásitos", 2019, "La familia Kim, todos desempleados, se interesa por la acaudalada familia Park...", "Drama"),
    Pelicula(3, "El Padrino", 1972, "Don Vito Corleone es el respetado y temido jefe de una de las cinco familias...", "Drama"),
    Pelicula(4, "Origen", 2010, "Dom Cobb es un ladrón capaz de robar secretos del subconsciente mientras se sueña...", "Ciencia ficción"),
    Pelicula(5, "El viaje de Chihiro", 2001, "Chihiro, de diez años, queda atrapada en un mundo de espíritus...", "Animación")
)

val generos = listOf("Ciencia ficción", "Drama", "Animación")
