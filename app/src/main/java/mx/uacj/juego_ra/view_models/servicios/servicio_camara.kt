package mx.uacj.juego_ra.view_models.servicios

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ServicioCamara: ViewModel(){
    private var _surface_vista_camara = MutableStateFlow<SurfaceRequest?>(null)
    var surface_vista_camara: StateFlow<SurfaceRequest?> = _surface_vista_camara

    private val previsualizacion = Preview.Builder().build().apply {
        setSurfaceProvider { nueva_peticion_de_surface ->
            _surface_vista_camara.update { nueva_peticion_de_surface }

        }
    }

    suspend fun conectar_con_camara(contexto_aplicaicon: Context, dueño_del_ciclo_de_vida: LifecycleOwner){
        val proceso_camara_proveedor = ProcessCameraProvider.awaitInstance(contexto_aplicaicon)

        proceso_camara_proveedor.bindToLifecycle(
            dueño_del_ciclo_de_vida, CameraSelector.DEFAULT_FRONT_CAMERA, previsualizacion
        )

        try { awaitCancellation() } finally {
            proceso_camara_proveedor.unbindAll()
        }
    }

}