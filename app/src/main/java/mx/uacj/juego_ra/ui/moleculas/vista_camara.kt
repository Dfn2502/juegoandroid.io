package mx.uacj.juego_ra.ui.moleculas

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.uacj.juego_ra.modelos.servicios.ServicioCamara

@Composable
fun VistaCamara(
        servicio_camara: ServicioCamara,
        modificador: Modifier = Modifier,
        dueño_ciclo_vida: LifecycleOwner = LocalLifecycleOwner.current
){
    val surface by servicio_camara.peticion_surface.collectAsStateWithLifecycle()
    val contexto = LocalContext.current

    LaunchedEffect(dueño_ciclo_vida) {
        servicio_camara.conectar_con_camara(contexto.applicationContext, dueño_ciclo_vida)
    }

    surface?.let { peticion ->
        CameraXViewfinder(
            surfaceRequest = peticion,
            modificador
        )

    }
}