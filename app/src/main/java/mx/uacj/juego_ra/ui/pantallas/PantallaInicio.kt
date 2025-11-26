package mx.uacj.juego_ra.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.uacj.juego_ra.R
import mx.uacj.juego_ra.ui.theme.Juego_raTheme

@Composable
fun PantallaInicio(
    onIniciarClick: () -> Unit,
    modificador: Modifier = Modifier
) {
    Box(
        modifier = modificador
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(R.drawable.imagen_1),
            contentDescription = "Fondo de misterio del juego.",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.5f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround // Espacia los elementos verticalmente
        ) {
            // Título del juego
            Text(
                text = "Las Notas de los Reprobados",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp, // Mejora el espaciado entre líneas si el título se parte
                style = MaterialTheme.typography.displaySmall.copy(
                    shadow = androidx.compose.ui.graphics.Shadow(
                        color = Color.Black.copy(alpha = 0.7f),
                        offset = androidx.compose.ui.geometry.Offset(4f, 4f),
                        blurRadius = 8f
                    )
                )
            )

            // Botón para iniciar la experiencia
            Button(
                onClick = onIniciarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "INICIAR INVESTIGACIÓN",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

// Preview para ver cómo se ve nuestra pantalla en el editor
@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    Juego_raTheme {
        PantallaInicio(onIniciarClick = {})
    }
}