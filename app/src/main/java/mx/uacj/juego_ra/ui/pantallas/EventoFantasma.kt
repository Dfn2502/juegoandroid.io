package mx.uacj.juego_ra.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mx.uacj.juego_ra.ui.controladores.Rutas
import mx.uacj.juego_ra.view_models.ControladorGeneral
import mx.uacj.juego_ra.view_models.NavegacionEvento

@Composable
fun EventoFantasmaPantalla(
    navegador: NavHostController,
    controlador_general: ControladorGeneral
) {
    val eventoNavegacion by controlador_general.eventoDeNavegacion.collectAsStateWithLifecycle()

    LaunchedEffect(eventoNavegacion) {
        if (eventoNavegacion == NavegacionEvento.VolverAMapa) {
            navegador.navigate(Rutas.PRINCIPAL) {
                popUpTo(Rutas.JUEGO_GRAPH) { inclusive = false }
                launchSingleTop = true
            }
            controlador_general.eventoDeNavegacionConsumido()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(250.dp)
                .background(Color.White.copy(alpha = 0.15f))
        )

        Text(
            text = "“No queríamos reprobar, queríamos entender.. ” \n Lo peor puede pasar cuando la creatividad y la ingenieria no se entienden",
            color = Color(0xFFBB86FC),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 280.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )

        Button(
            onClick = { controlador_general.avanzarSiguientePista() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp)
        ) {
            Text("Leer nota")
        }
    }
}
