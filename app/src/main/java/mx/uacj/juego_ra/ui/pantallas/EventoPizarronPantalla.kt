package mx.uacj.juego_ra.ui.pantallas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import mx.uacj.juego_ra.R
import mx.uacj.juego_ra.ui.controladores.Rutas
import mx.uacj.juego_ra.view_models.NavegacionEvento
import mx.uacj.juego_ra.view_models.ControladorGeneral

@Composable
fun EventoPizarronPantalla(
    navegador: NavHostController,
    controlador_general: ControladorGeneral
) {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val gyroscopeSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    var resuelto by remember { mutableStateOf(false) }
    var estaAlineado by remember { mutableStateOf(false) }

    val pista by controlador_general.pista_actual.collectAsStateWithLifecycle()

    // --- Sensor gyroscope ---
    DisposableEffect(gyroscopeSensor) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (resuelto || event == null) return
                val pitch = event.values[0]
                val roll = event.values[1]
                estaAlineado = kotlin.math.abs(pitch) < 0.1f && kotlin.math.abs(roll) < 0.1f
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, gyroscopeSensor, SensorManager.SENSOR_DELAY_UI)
        onDispose { sensorManager.unregisterListener(listener) }
    }

    LaunchedEffect(estaAlineado) {
        if (estaAlineado && !resuelto) {
            kotlinx.coroutines.delay(2000)
            if (estaAlineado) {
                resuelto = true

                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(200)
                }

                val mediaPlayer = MediaPlayer.create(context, R.raw.audio_luis)
                mediaPlayer.setOnCompletionListener { it.release() }
                mediaPlayer.start()
            }
        }
    }

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

    val colorFondo by animateColorAsState(
        if (estaAlineado) Color(0xFF003300) else Color(0xFF330000) // Verde oscuro / Rojo oscuro
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (!resuelto) {
                Text(
                    text = "Alinea los 0s y 1s...",
                    color = Color.White,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                PizarronBinarioUI(estaAlineado)

                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Inclina el teléfono hasta que el pizarrón se ilumine de verde.",
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = "\"El código se escribía solo… como si alguien más estuviera programando conmigo.\n" +
                            "La defensora del silencio resguarda algo perverso",
                    color = Color.Cyan,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = { controlador_general.avanzarSiguientePista() },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(60.dp)
                ) {
                    Text("Continuar", fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun PizarronBinarioUI(estaAlineado: Boolean) {
    val offsetX by animateDpAsState(if (estaAlineado) 0.dp else 12.dp)
    val colorTexto by animateColorAsState(if (estaAlineado) Color.Green else Color.White)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        repeat(10) { fila ->
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(20) { col ->
                    Text(
                        text = if ((fila + col) % 2 == 0) "0" else "1",
                        fontSize = 18.sp,
                        color = colorTexto,
                        modifier = Modifier.offset(x = offsetX, y = 0.dp)
                    )
                }
            }
        }
    }
}
