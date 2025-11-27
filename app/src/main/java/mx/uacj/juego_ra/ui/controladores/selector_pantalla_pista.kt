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
    // Obtenemos la pista actual del ViewModel
    val pista_actual by controlador_general.pista_actual.collectAsStateWithLifecycle()
    // Usamos 'let' para trabajar de forma segura con la pista, evitando el '!!'
    pista_actual?.let { pista ->
        when (pista.cuerpo.tipo) {
            TiposDePistas.texto -> {
                // --- INICIO DE LA CORRECCIÓN ---
                // Se nombran TODOS los parámetros para evitar errores de sintaxis.
                InformacionVista(
                    informacion_a_mostrar = pista.cuerpo as Informacion, // Suponiendo que el parámetro se llama 'informacion'
                    controlador_general = controlador_general,
                    navegador = navegador
                )
                // --- FIN DE LA CORRECCIÓN ---
            }

            TiposDePistas.interactiva -> {
                InformacionInteractivaVista(
                    navegador = navegador,
                    informacion_interactiva = pista.cuerpo as InformacionInteractiva,
                    controlador_general = controlador_general
                )
            }

            TiposDePistas.camara -> {
                TODO("Pantalla para la cámara no implementada")
            }

            TiposDePistas.agitar_telefono -> {
                TODO("Pantalla para agitar el teléfono no implementada")
            }
        }
    }
}