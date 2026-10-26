package com.pmdm.ud3.estado.tareas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/*
 * Apartado 3.6 — ViewModel y estado de la UI.
 * El código es el de los apuntes, sin cambios.
 *
 * Fíjate en que este fichero NO importa nada de Compose: el ViewModel no sabe
 * cómo se pinta la pantalla, solo gestiona los datos y la lógica.
 */

// Modelo
data class Tarea(
    val id: Int,
    val texto: String,
    val completada: Boolean = false
)

// Estado de la UI: TODO lo que la pantalla necesita para dibujarse, en un único objeto
data class TareasUiState(
    val tareas: List<Tarea> = emptyList(),
    val nuevaTareaTexto: String = "",
    val cargando: Boolean = false
)

// ViewModel
class TareasViewModel : ViewModel() {

    // StateFlow: emite el estado actual de la UI.
    // _uiState (privado y mutable) solo lo cambia el ViewModel;
    // uiState (público y de solo lectura) es lo que observa la pantalla.
    private val _uiState = MutableStateFlow(TareasUiState())
    val uiState: StateFlow<TareasUiState> = _uiState.asStateFlow()

    private var siguienteId = 1

    fun actualizarTextoNuevaTarea(texto: String) {
        _uiState.update { it.copy(nuevaTareaTexto = texto) }
    }

    fun agregarTarea() {
        val texto = _uiState.value.nuevaTareaTexto.trim()
        if (texto.isNotEmpty()) {
            _uiState.update { estado ->
                estado.copy(
                    tareas = estado.tareas + Tarea(id = siguienteId++, texto = texto),
                    nuevaTareaTexto = ""
                )
            }
        }
    }

    fun toggleTarea(id: Int) {
        _uiState.update { estado ->
            estado.copy(
                tareas = estado.tareas.map { tarea ->
                    if (tarea.id == id) tarea.copy(completada = !tarea.completada)
                    else tarea
                }
            )
        }
    }

    fun eliminarTarea(id: Int) {
        _uiState.update { estado ->
            estado.copy(tareas = estado.tareas.filter { it.id != id })
        }
    }
}
