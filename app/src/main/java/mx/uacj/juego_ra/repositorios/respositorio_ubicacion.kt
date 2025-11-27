package mx.uacj.juego_ra.repositorios

import androidx.compose.runtime.MutableState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface RepositorioUbicacion {
    val ubicacion: StateFlow<Location?>
    fun actualizarUbicacion(nueva: Location)
}

class InstanciaRepositorioUbicacion @Inject constructor() : RepositorioUbicacion {

    private val _ubicacion = MutableStateFlow<Location?>(null)
    override val ubicacion: StateFlow<Location?> = _ubicacion.asStateFlow()

    override fun actualizarUbicacion(nueva: Location) {
        _ubicacion.value = nueva
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositorioUbicacionModule {

    @Provides
    @Singleton
    fun provideRepositorioUbicacion(): RepositorioUbicacion {
        return InstanciaRepositorioUbicacion()
    }
}

