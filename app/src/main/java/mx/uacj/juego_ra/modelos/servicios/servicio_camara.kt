package mx.uacj.juego_ra.modelos.servicios

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
    private var _peticion_surface = MutableStateFlow<SurfaceRequest?>(null)
    val peticion_surface: StateFlow<SurfaceRequest?> = _peticion_surface

    private val previsualizacion_camara = Preview.Builder().build().apply {
        setSurfaceProvider { nueva_peticion_surface ->
            _peticion_surface.update { nueva_peticion_surface }
        }
    }

    suspend fun conectar_con_camara(contexto_app: Context, dueño_ciclo_vida: LifecycleOwner){

        val proceso_camara_proveedor = ProcessCameraProvider.awaitInstance(contexto_app)
        proceso_camara_proveedor.bindToLifecycle(
            dueño_ciclo_vida, CameraSelector.DEFAULT_BACK_CAMERA, previsualizacion_camara
        )

        // Cancellation signals we're done with the camera
        try { awaitCancellation() } finally { proceso_camara_proveedor.unbindAll() }
    }

}
