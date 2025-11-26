package mx.uacj.juego_ra.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.sourceInformation
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.uacj.juego_ra.modelos.Pista
import mx.uacj.juego_ra.repositorios.RepositorioInformacionGeneral
import mx.uacj.juego_ra.repositorios.estaticos.RepositorioPruebas.pistas
import javax.inject.Inject

@HiltViewModel
class ControladorGeneral @Inject constructor(
    private val infomracion_general: RepositorioInformacionGeneral
)  : ViewModel()  {

    var pista_actual: State<Pista?> = infomracion_general.pista_seleccionada

    fun seleccionar_pista(pista: Pista){
        infomracion_general.pista_seleccionada.value = pista
    }

    fun seleccionar_pista(pista_id: String){
        for(pista in pistas){
            if(pista.nombre == pista_id){
                infomracion_general.pista_seleccionada.value = pista
                break
            }
        }
    }


    fun el_usuario_ha_identificado_la_pista(pista: Pista){
        infomracion_general.pistas_identificadas.value
    }
}