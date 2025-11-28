package mx.uacj.juego_ra.ui.pantallas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.uacj.juego_ra.R
import mx.uacj.juego_ra.ui.controladores.Rutas
import mx.uacj.juego_ra.view_models.ControladorGeneral
import kotlin.math.sqrt

private enum class EstadoFinal {
    ESPERANDO_AGITACION,
    REPRODUCIENDO_AUDIO,
    CONCLUSION
}

private const val AGITACIONES_NECESARIAS = 3
private const val UMBRAL_ACELERACION = 15f
private const val COOLDOWN_AGITACION_MS = 1000L

@Composable
fun EventoFinalPantalla(
    navegador: NavHostController,
    controlador_general: ControladorGeneral
) {
    val context = LocalContext.current
    var estado by remember { mutableStateOf(EstadoFinal.ESPERANDO_AGITACION) }
    var agitacionesRestantes by remember { mutableIntStateOf(AGITACIONES_NECESARIAS) }
    var enCooldown by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(estado) {
        if (estado != EstadoFinal.ESPERANDO_AGITACION) return@DisposableEffect onDispose {}
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        var acceleration = 0f
        var currentAcceleration = SensorManager.GRAVITY_EARTH
        var lastAcceleration = SensorManager.GRAVITY_EARTH

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (enCooldown) return
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                lastAcceleration = currentAcceleration
                currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                val delta = currentAcceleration - lastAcceleration
                acceleration = acceleration * 0.9f + delta

                if (acceleration > UMBRAL_ACELERACION) {
                    enCooldown = true
                    val nuevasAgitaciones = agitacionesRestantes - 1
                    agitacionesRestantes = nuevasAgitaciones
                    if (nuevasAgitaciones <= 0) {
                        estado = EstadoFinal.REPRODUCIENDO_AUDIO
                    } else {
                        coroutineScope.launch {
                            delay(COOLDOWN_AGITACION_MS)
                            enCooldown = false
                        }
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        onDispose { sensorManager.unregisterListener(listener) }
    }

    DisposableEffect(estado) {
        if (estado != EstadoFinal.REPRODUCIENDO_AUDIO) return@DisposableEffect onDispose {}
        val mediaPlayer = MediaPlayer.create(context, R.raw.audio_final_ana)
        mediaPlayer.setOnCompletionListener { estado = EstadoFinal.CONCLUSION }
        mediaPlayer.start()
        onDispose {
            if (mediaPlayer.isPlaying) mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = estado,
            label = "transicion_evento_final",
            transitionSpec = {
                fadeIn(animationSpec = tween(600)) togetherWith fadeOut(animationSpec = tween(600))
            }
        ) { targetState ->
            when (targetState) {
                EstadoFinal.ESPERANDO_AGITACION -> PantallaErrorSistema(agitacionesRestantes)
                EstadoFinal.REPRODUCIENDO_AUDIO -> PantallaAudioEnProgreso()
                EstadoFinal.CONCLUSION -> PantallaConclusion(
                    onReiniciar = {
                        controlador_general.reiniciarJuego()
                        navegador.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.JUEGO_GRAPH) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PantallaErrorSistema(agitacionesRestantes: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SYSTEM ERROR",
            color = Color.Red,
            fontSize = 48.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(40.dp))
        if (agitacionesRestantes < AGITACIONES_NECESARIAS) {
            Text(
                text = "REINICIO FORZADO EN CURSO...",
                color = Color.Yellow,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$agitacionesRestantes",
                color = Color.Yellow,
                fontSize = 80.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "AGITACIONES RESTANTES",
                color = Color.Yellow,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "Agita el teléfono para reiniciar el sistema",
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PantallaAudioEnProgreso() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = Color.Cyan)
        Spacer(Modifier.height(40.dp))
        Text(
            text = "\"No estábamos locos. El Proyecto SINAPSIS no reprobó… evolucionó. Y ahora tú también formas parte.\"",
            color = Color.Cyan,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )
    }
}

@Composable
fun PantallaConclusion(onReiniciar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Archivo encontrado:\nLas notas de los reprobados",
            color = Color(0xFF00FFFF),
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )
        Spacer(Modifier.height(16.dp))
        Text(text = "Acceso Concedido.", color = Color.Green, fontSize = 24.sp)
        Spacer(Modifier.height(60.dp))
        Button(onClick = onReiniciar) {
            Text("Reiniciar Simulación")
        }
    }
}
