package com.pmdm.ud3.estado.peliculas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Apartado 3.7 — Uso de UiState en el ViewModel. Código de los apuntes.
 *
 * Añadido: la propiedad `simularFallo` y su función, para poder provocar
 * el estado Error desde la pantalla.
 */
class PeliculasViewModel : ViewModel() {

    private val repositorio = RepositorioPeliculas()

    private val _uiState = MutableStateFlow<UiState<List<Pelicula>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Pelicula>>> = _uiState.asStateFlow()

    private val _simularFallo = MutableStateFlow(false)
    val simularFallo: StateFlow<Boolean> = _simularFallo.asStateFlow()

    init {
        // Se carga al crear el ViewModel. Como el ViewModel sobrevive a rotaciones
        // y cambios de pestaña, NO se vuelve a cargar cada vez que entras.
        cargarPeliculas()
    }

    fun cargarPeliculas() {
        // viewModelScope: corrutina que se cancela sola si el ViewModel se destruye
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val peliculas = repositorio.obtenerPeliculas()
                _uiState.value = UiState.Success(peliculas)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun cambiarSimularFallo(valor: Boolean) {
        _simularFallo.value = valor
        repositorio.simularFallo = valor
    }
}
