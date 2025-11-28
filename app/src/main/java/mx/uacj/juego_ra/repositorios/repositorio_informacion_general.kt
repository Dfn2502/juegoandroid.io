package mx.uacj.juego_ra.repositorios

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.uacj.juego_ra.modelos.Pista
import mx.uacj.juego_ra.repositorios.estaticos.RepositorioPruebas
import javax.inject.Singleton

interface RepositorioInformacionGeneral {

    fun obtenerPistaActual(): Pista?

    fun avanzarProgreso()
}

class InstanciaRepositorioInformacionGeneral(
    private val todasLasPistas: List<Pista>
) : RepositorioInformacionGeneral {

    private var progresoActual: Int = 0

    override fun obtenerPistaActual(): Pista? {
        return todasLasPistas.getOrNull(progresoActual)
    }

    override fun avanzarProgreso() {
        progresoActual++
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositorioInformacionGeneralModule {
    @Provides
    @Singleton
    fun proveerRepositorio(): RepositorioInformacionGeneral {
        return InstanciaRepositorioInformacionGeneral(RepositorioPruebas._pistas)
    }
}
