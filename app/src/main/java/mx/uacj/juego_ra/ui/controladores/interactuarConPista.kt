import androidx.navigation.NavHostController
import mx.uacj.juego_ra.modelos.InformacionInteractiva
import mx.uacj.juego_ra.modelos.Pista
import mx.uacj.juego_ra.ui.controladores.Rutas
import mx.uacj.juego_ra.view_models.ControladorGeneral

fun interactuarConPista(
    pista: Pista?,
    navegador: NavHostController,
    controlador: ControladorGeneral
) {
    pista ?: return

    when (val cuerpo = pista.cuerpo) {
        is InformacionInteractiva -> {
            navegador.navigate(cuerpo.ruta)
        }

        else -> {
            controlador.avanzarSiguientePista()

            navegador.navigate(Rutas.PRINCIPAL) {
                popUpTo(Rutas.JUEGO_GRAPH) { inclusive = false }
                launchSingleTop = true
            }
        }
    }
}
