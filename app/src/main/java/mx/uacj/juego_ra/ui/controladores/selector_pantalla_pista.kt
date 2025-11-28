package mx.uacj.juego_ra.ui.controladores

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mx.uacj.juego_ra.modelos.Informacion
import mx.uacj.juego_ra.modelos.InformacionInteractiva
import mx.uacj.juego_ra.modelos.TiposDePistas
import mx.uacj.juego_ra.ui.organismos.InformacionInteractivaVista
import mx.uacj.juego_ra.ui.organismos.InformacionVista
import mx.uacj.juego_ra.view_models.ControladorGeneral

@Composable
fun SeleccionarPantallaPista(
    navegador: NavHostController,
    modificador: Modifier = Modifier,
    controlador_general: ControladorGeneral = hiltViewModel()
) {
    val pista_actual by controlador_general.pista_actual.collectAsStateWithLifecycle()
    pista_actual?.let { pista ->
        when (pista.cuerpo.tipo) {
            TiposDePistas.texto -> {
                InformacionVista(
                    informacion_a_mostrar = pista.cuerpo as Informacion,
                    controlador_general = controlador_general,
                    navegador = navegador
                )
            }

            TiposDePistas.interactiva -> {
                InformacionInteractivaVista(
                    navegador = navegador,
                    informacion_interactiva = pista.cuerpo as InformacionInteractiva,
                    controlador_general = controlador_general
                )
            }

            else -> {
            }
        }
    }
}