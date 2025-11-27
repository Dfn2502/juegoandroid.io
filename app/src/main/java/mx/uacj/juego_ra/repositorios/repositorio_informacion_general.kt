package mx.uacj.juego_ra.repositorios

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.uacj.juego_ra.modelos.Pista
import mx.uacj.juego_ra.repositorios.estaticos.RepositorioPruebas // ¡IMPORTANTE! Importamos tus pistas
import javax.inject.Singleton

/**
 * Interfaz que define las capacidades del repositorio.
 * Abstrae la implementación para que los ViewModels no necesiten conocer los detalles.
 */
interface RepositorioInformacionGeneral {
    /**
     * Devuelve la pista actual basada en el progreso del jugador.
     * Si ya se completaron todas las pistas, devuelve null.
     */
    fun obtenerPistaActual(): Pista?

    /**
     * Avanza el progreso del juego a la siguiente pista.
     */
    fun avanzarProgreso()
}

/**
 * Implementación concreta de la interfaz.
 * Esta es la clase que realmente contiene la lógica del estado del juego.
 */
class InstanciaRepositorioInformacionGeneral(
    private val todasLasPistas: List<Pista>
) : RepositorioInformacionGeneral {

    // Guarda el índice de la pista en la que se encuentra el jugador. Empieza en 0.
    private var progresoActual: Int = 0

    override fun obtenerPistaActual(): Pista? {
        // getOrNull es seguro: si el índice está fuera de los límites, devuelve null.
        return todasLasPistas.getOrNull(progresoActual)
    }

    override fun avanzarProgreso() {
        progresoActual++
    }
}

/**
 * Módulo de Hilt para proveer la instancia del repositorio como un Singleton.
 * ESTA ES LA PARTE CLAVE: Le decimos a Hilt que use tus pistas de RepositorioPruebas.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositorioInformacionGeneralModule {
    @Provides
    @Singleton
    fun proveerRepositorio(): RepositorioInformacionGeneral {
        // Usamos la lista 'pistas' de tu objeto 'RepositorioPruebas' para crear la instancia.
        return InstanciaRepositorioInformacionGeneral(RepositorioPruebas._pistas)
    }
}
