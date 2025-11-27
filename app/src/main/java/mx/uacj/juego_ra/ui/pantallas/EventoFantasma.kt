package mx.uacj.juego_ra.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mx.uacj.juego_ra.ui.controladores.Rutas
import mx.uacj.juego_ra.view_models.ControladorGeneral
import mx.uacj.juego_ra.view_models.NavegacionEvento

@Composable
fun EventoFantasmaPantalla(
    navegador: NavHostController,
    // Usamos el ViewModel compartido del grafo de navegación para asegurar consistencia.
    controlador_general: ControladorGeneral
) {
    val eventoNavegacion by controlador_general.eventoDeNavegacion.collectAsStateWithLifecycle()

    LaunchedEffect(eventoNavegacion) {
        if (eventoNavegacion == NavegacionEvento.VolverAMapa) {
            // ...entonces navegamos.
            navegador.navigate(Rutas.PRINCIPAL) {
                popUpTo(Rutas.JUEGO_GRAPH) { inclusive = false }
                launchSingleTop = true
            }
            // 3. MUY IMPORTANTE: Le decimos al ViewModel que ya hemos manejado el evento.
            //    Esto evita que la navegación se dispare de nuevo si la pantalla se recompone.
            controlador_general.eventoDeNavegacionConsumido()
        }
    }
    // --- FIN DE LA CORRECCIÓN CLAVE ---

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Proyección de Ana en RA",
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = {
                // 4. El botón ahora solo tiene UNA responsabilidad: notificar el evento.
                controlador_general.avanzarSiguientePista()
            }
        ) {
            Text("Leer nota")
        }
    }
}