package mx.uacj.juego_ra.ui.pantallas

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import mx.uacj.juego_ra.modelos.*
import mx.uacj.juego_ra.ui.organismos.InformacionInteractivaVista
import mx.uacj.juego_ra.view_models.ControladorGeneral

@Composable
fun PantallaEventoGen(
    navegador: NavHostController,
    controlador_general: ControladorGeneral = hiltViewModel()
) {
    val pista = controlador_general.pista_actual.value
    val context = LocalContext.current

    if (pista == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("¡Juego completado!")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navegador.popBackStack() }) {
                Text("Regresar al mapa")
            }
        }
        return
    }

    LaunchedEffect(Unit) {
        val vibrator = context.getSystemService(Vibrator::class.java)
        vibrator?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                it.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                it.vibrate(300)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = pista.nombre,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(24.dp))

        when (val cuerpo = pista.cuerpo) {
            is Informacion -> {
                Text(text = cuerpo.texto, style = MaterialTheme.typography.bodyLarge)
                cuerpo.imagen?.let { imgRes ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = imgRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        controlador_general.avanzarSiguientePista()
                        navegador.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Completar pista")
                }
            }
            is InformacionInteractiva -> {
                InformacionInteractivaVista(
                    navegador,
                    cuerpo,
                    controlador_general = controlador_general
                )
            }
        }
    }
}
