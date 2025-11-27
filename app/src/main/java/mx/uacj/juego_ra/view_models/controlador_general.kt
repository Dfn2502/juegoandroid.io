package mx.uacj.juego_ra.view_models

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.uacj.juego_ra.modelos.Pista
import mx.uacj.juego_ra.repositorios.RepositorioInformacionGeneral
import mx.uacj.juego_ra.repositorios.estaticos.RepositorioPruebas
import javax.inject.Inject

@HiltViewModel
class ControladorGeneral @Inject constructor() : ViewModel() {

    private val _pista_actual = MutableStateFlow<Pista?>(null)
    val pista_actual = _pista_actual.asStateFlow()
    private val _eventoDeNavegacion = MutableStateFlow<NavegacionEvento?>(null)
    val eventoDeNavegacion = _eventoDeNavegacion.asStateFlow()

    init {
        Log.d("ControladorGeneral", "ViewModel inicializado. Reiniciando estado del juego.")
        RepositorioPruebas.reiniciarPistas()
        cargarPistaActual()
    }

    private fun cargarPistaActual() {
        val pistaDelRepositorio = RepositorioPruebas.obtenerPistaActual()
        _pista_actual.value = pistaDelRepositorio?.copy()
        Log.d("ControladorGeneral", "Pista cargada: ${_pista_actual.value?.nombre ?: "Ninguna"}")
    }
    fun reiniciarJuego() {
        // Llama a la función correspondiente en el repositorio
        RepositorioPruebas.reiniciarPistas()

        // Actualiza inmediatamente el estado en el ViewModel para reflejar el cambio.
        // Esto asegura que si algo depende de la pista actual, se actualice al instante.
        _pista_actual.value = RepositorioPruebas.obtenerPistaActual()
    }
    fun avanzarSiguientePista() {
        Log.d("ControladorGeneral", "1. Avanzando pista en el repositorio...")
        RepositorioPruebas.avanzarPista()

        Log.d("ControladorGeneral", "2. Recargando el StateFlow con la nueva pista...")
        cargarPistaActual()

        Log.d("ControladorGeneral", "3. Disparando evento de navegación...")
        // Disparamos el evento para que la UI reaccione y navegue.
        _eventoDeNavegacion.value = NavegacionEvento.VolverAMapa
    }

    // Función para que la UI nos diga que ya manejó el evento de navegación.
    fun eventoDeNavegacionConsumido() {
        Log.d("ControladorGeneral", "4. Evento de navegación consumido. Reiniciando.")
        _eventoDeNavegacion.value = null
    }
}

// Clase sellada para manejar diferentes eventos de navegación en el futuro si es necesario.
sealed class NavegacionEvento {
    object VolverAMapa : NavegacionEvento()
}