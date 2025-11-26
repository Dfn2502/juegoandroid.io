package mx.uacj.juego_ra.ui.organismos

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.lang.Math.abs

@Composable
fun DetectorAgitamiento(modificador: Modifier = Modifier,
                        meta_de_agitadas: Int,
                        al_llegar_a_la_meta: () -> Unit
){
    val contexto = LocalContext.current
    var contador_agitadas by remember { mutableStateOf(0) }
    val sensibilidad = 160

    DisposableEffect(Unit) {
        val gestor_sensor = contexto.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor_agitamiento = gestor_sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val escucha = object: android.hardware.SensorEventListener {
            override fun onSensorChanged(evento: SensorEvent?) {
                if(evento != null){
                    val x = evento.values[0]
                    val y = evento.values[1]
                    val z = evento.values[2]

                    val velocidad_movimiento = abs(x) + abs(y) + abs(z) /// -4 -> 4    4 -> 4
                    if(velocidad_movimiento > sensibilidad){
                        Log.v("SENSOR_VELOCIDAD", "LA velocidad fue de ${velocidad_movimiento}")
                        contador_agitadas = contador_agitadas + 1

                        if(contador_agitadas >= meta_de_agitadas){
                            al_llegar_a_la_meta()
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
        }

        gestor_sensor.registerListener(escucha, sensor_agitamiento, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            gestor_sensor.unregisterListener(escucha)
        }

    }

    /*Column {
        Text("Cantidad de agitadas ${contador_agitadas} y la meta es ${meta_de_agitadas}")
    }*/
}