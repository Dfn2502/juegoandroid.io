package mx.uacj.juego_ra.ui.atomos

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.uacj.juego_ra.view_models.servicios.ServicioCamara

@Composable
fun VistaCamara(
        servicio_de_camara: ServicioCamara = ServicioCamara(),
        modificador: Modifier = Modifier,
        dueño_del_ciclo_de_vida: LifecycleOwner = LocalLifecycleOwner.current
){
    val superfice_del_dibuajdo by servicio_de_camara.surface_vista_camara.collectAsStateWithLifecycle()
    val contexto = LocalContext.current

    LaunchedEffect(dueño_del_ciclo_de_vida) {
        servicio_de_camara.conectar_con_camara(contexto.applicationContext, dueño_del_ciclo_de_vida)
    }

    superfice_del_dibuajdo?.let { peticion ->
        CameraXViewfinder(
            surfaceRequest = peticion,
            modificador
        )
    }
}