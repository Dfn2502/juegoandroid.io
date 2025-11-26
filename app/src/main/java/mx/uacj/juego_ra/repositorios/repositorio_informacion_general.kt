package mx.uacj.juego_ra.repositorios

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.uacj.juego_ra.modelos.Pista
import java.util.Collections.emptyList
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object repositorio_informacion_general_singleton{
    @Provides
    @Singleton
    fun crea_repostiorio_de_informacion_general(): RepositorioInformacionGeneral{
        return InstanciaRepositorioInformacionGeneral()
    }
}


class InstanciaRepositorioInformacionGeneral(
    override val pistas_identificadas: MutableState<List<Pista>> = mutableStateOf(emptyList()),
    override val pista_seleccionada: MutableState<Pista?> = mutableStateOf(null)

): RepositorioInformacionGeneral {}

interface RepositorioInformacionGeneral {
    val pistas_identificadas: MutableState<List<Pista>>
    val pista_seleccionada: MutableState<Pista?>
}
