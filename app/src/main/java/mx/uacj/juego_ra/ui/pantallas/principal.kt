package mx.uacj.juego_ra.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import interactuarConPista
import mx.uacj.juego_ra.ui.atomos.MapaMapLibre
import mx.uacj.juego_ra.view_models.ControladorGeneral
import mx.uacj.juego_ra.view_models.GestorUbicacion

@Composable
fun Principal(
    navegador: NavHostController,
    modificador: Modifier = Modifier,
    gestor_ubicacion: GestorUbicacion,
    controlador_general: ControladorGeneral
) {
    val ubicacionLocal by gestor_ubicacion.ubicacion_actual.collectAsStateWithLifecycle()
    val pistaActual by controlador_general.pista_actual.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(
            modifier = modificador
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (ubicacionLocal != null && pistaActual != null) {
                key(pistaActual!!.nombre) {
                    MapaMapLibre(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        ubicacionUsuario = ubicacionLocal!!
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "🔍 Pista: ${pistaActual!!.nombre}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    val distancia = ubicacionLocal!!.distanceTo(pistaActual!!.ubicacion)
                    val textoDistancia = when {
                        distancia < pistaActual!!.distancia_minima -> "¡Estás en el lugar!"
                        distancia < pistaActual!!.distancia_maxima * 0.25 -> "🔥 ¡Te estás quemando!"
                        distancia < pistaActual!!.distancia_maxima * 0.50 -> "♨️ Caliente"
                        distancia < pistaActual!!.distancia_maxima * 0.75 -> "🌡️ Tibio"
                        distancia < pistaActual!!.distancia_maxima -> "❄️ Frío"
                        else -> "❄️❄️ Estás muy lejos"
                    }

                    Text(
                        text = "$textoDistancia (Distancia: ${distancia.toInt()} m)",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    if (distancia < pistaActual!!.distancia_minima + 5f) {
                        Button(
                            onClick = {
                                interactuarConPista(
                                    pistaActual,
                                    navegador,
                                    controlador_general
                                )
                            },
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(0.7f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "Interactuar con la pista",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
