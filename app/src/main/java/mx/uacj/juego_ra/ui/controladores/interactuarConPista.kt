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
            // Navega a la ruta definida en la pista interactiva
            navegador.navigate(cuerpo.ruta)
            // NO avanzamos la pista aquí, la pantalla destino debe hacerlo
        }

        else -> {
            // Para pistas normales, avanzamos y regresamos al mapa
            controlador.avanzarSiguientePista()

            navegador.navigate(Rutas.PRINCIPAL) {
                popUpTo(Rutas.JUEGO_GRAPH) { inclusive = false }
                launchSingleTop = true
            }
        }
    }
}
