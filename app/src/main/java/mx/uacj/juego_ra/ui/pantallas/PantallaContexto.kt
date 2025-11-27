package mx.uacj.juego_ra.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import mx.uacj.juego_ra.ui.theme.Juego_raTheme
import mx.uacj.juego_ra.view_models.ControladorGeneral
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

    Surface(
        modifier = modificador.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "INFORME CLASIFICADO",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Archivo: Las Notas de los Reprobados",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Tu Misión:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Asumes el rol de un investigador universitario. Tu objetivo es descubrir qué sucedió con cinco alumnos de la carrera de DDMI seleccionados para la materia experimental Proyecto SINAPSIS_2.1...\nDirígete a la entrada del campus para iniciar.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "ADVERTENCIA: Al iniciar, tu teléfono vibrará con un mensaje...",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Button(
                onClick = { onContinuarClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                enabled = ubicacion != null // Habilita solo si ya llegó ubicación
            ) {
                Text("COMENZAR INVESTIGACIÓN")
            }

            if (ubicacion == null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Esperando ubicación del dispositivo...")
            }
        }
    }
}
