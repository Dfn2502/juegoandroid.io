package mx.uacj.juego_ra.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.uacj.juego_ra.R

@Composable
fun PantallaDialogoPista(
    navegador: NavHostController,
    textoDialogo: String,
    textoBoton: String,
    rutaSiguiente: String
) {
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Asume que tienes una imagen 'logo_ddmi_distorsionado' en tus drawables
            Image(
                painter = painterResource(id = R.drawable.logo_ddmi_distorsionado),
                contentDescription = "Logo DDMI Distorsionado",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = textoDialogo,
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(40.dp))
            Button(onClick = {
                // Navega a la siguiente pantalla en la cadena (ej. el Scanner)
                navegador.navigate(rutaSiguiente)
            }) {
                Text(textoBoton, fontSize = 18.sp)
            }
        }
    }
}