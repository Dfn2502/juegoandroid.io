package mx.uacj.juego_ra.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mx.uacj.juego_ra.view_models.GestorUbicacion

@Composable
fun PantallaContexto(
    navegador: NavHostController,
    onContinuarClick: () -> Unit,
    gestorUbicacion: GestorUbicacion = hiltViewModel(),
    modificador: Modifier = Modifier
) {
    val ubicacion by gestorUbicacion.ubicacion_actual.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Box(
        modifier = modificador
            .fillMaxSize()
            .background(Color(0xFF0A0A0A))
            .padding(16.dp)
    ) {

        // Partículas de fondo sutiles
        ParticulasFlotantes(context = "contexto")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "INFORME CLASIFICADO",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE63946),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Archivo: Las Notas de los Reprobados",
                    fontSize = 20.sp,
                    color = Color(0xFFFAFAFA),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "TU MISIÓN",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00B4D8)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Asumes el rol de un investigador universitario. Tu objetivo es descubrir qué sucedió con cinco alumnos de la carrera de DDMI seleccionados para la materia experimental Proyecto SINAPSIS_2.1...\nDirígete a la entrada del campus para iniciar.",
                    fontSize = 18.sp,
                    color = Color(0xFFE0E0E0),
                    textAlign = TextAlign.Justify,
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "ADVERTENCIA: Al iniciar, tu teléfono vibrará con un mensaje...",
                    fontSize = 16.sp,
                    color = Color(0xFFFF6B6B),
                    textAlign = TextAlign.Center
                )
            }

            Button(
                onClick = { onContinuarClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                enabled = ubicacion != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00B4D8),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
            ) {
                Text(
                    text = "COMENZAR INVESTIGACIÓN",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (ubicacion == null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Esperando ubicación del dispositivo...",
                    fontSize = 16.sp,
                    color = Color(0xFFAAAAAA)
                )
            }
        }
    }
}

@Composable
fun ParticulasFlotantes(context: String) {

}
