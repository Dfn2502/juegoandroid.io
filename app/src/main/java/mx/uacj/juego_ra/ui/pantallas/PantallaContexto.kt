package mx.uacj.juego_ra.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.uacj.juego_ra.ui.theme.Juego_raTheme

@Composable
fun PantallaContexto(
    onContinuarClick: () -> Unit,
    modificador: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = modificador.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState), // Para que el texto largo sea deslizable
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // Empuja el botón hacia abajo
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
                    text = "Asumes el rol de un investigador universitario. Tu objetivo es descubrir qué sucedió con cinco alumnos de la carrera de Diseño Digital de Medios Interactivos (DDMI) seleccionados para una misteriosa materia experimental llamada \"Proyecto SINAPSIS_2.1\".\n\n" +
                            "A lo largo del campus, deberás visitar puntos clave, escanear objetos, resolver acertijos y recopilar pistas digitales que revelarán la verdad oculta detrás de su aparente locura colectiva.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify, // Justifica el texto para un look más formal
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "ADVERTENCIA: Al iniciar, tu teléfono vibrará con un mensaje:\n\n“Cinco fueron elegidos. Cinco fallaron. Encuentra sus notas antes de que te encuentren a ti.”\n\nBusca el primer código. Tu investigación comienza ahora.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // El botón se colocará al final gracias a Arrangement.SpaceBetween
            Button(
                onClick = onContinuarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("COMENZAR INVESTIGACIÓN")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaContexto() {
    Juego_raTheme {
        PantallaContexto(onContinuarClick = {})
    }
}
