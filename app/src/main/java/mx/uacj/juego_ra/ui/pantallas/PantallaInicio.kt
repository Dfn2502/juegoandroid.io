package mx.uacj.juego_ra.ui.pantallas

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import mx.uacj.juego_ra.R
import kotlin.random.Random

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
            contentDescription = "Fondo misterioso",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xAA000000), Color.Transparent, Color(0xAA000000)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        ParticulasFlotantes()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            GlitchText(
                text = "Las Notas de los Reprobados",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFFD700),
                lineHeight = 48.sp
            )

            Text(
                text = "Conviértete en investigador y descubre qué sucedió con los alumnos de DDMI. \nEscanea, investiga y sigue las pistas para revelar la verdad oculta.",
                fontSize = 18.sp,
                color = Color(0xFFE0E0E0),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier
                    .background(Color(0x55000000), RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )

            Button(
                onClick = onIniciarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBB86FC),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
            ) {
                Text(
                    text = "INICIAR INVESTIGACIÓN",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ParticulasFlotantes() {
    val particles = remember { List(30) { mutableStateOf(Offset(Random.nextFloat() * 1080f, Random.nextFloat() * 1920f)) } }
    val infiniteTransition = rememberInfiniteTransition()
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { state ->
            drawCircle(
                color = Color.Cyan.copy(alpha = alphaAnim),
                radius = 3.dp.toPx(),
                center = state.value
            )
            state.value = Offset(state.value.x, (state.value.y - 0.5f).let { if (it < 0) size.height else it })
        }
    }
}

@Composable
fun GlitchText(
    text: String,
    fontSize: androidx.compose.ui.unit.TextUnit,
    fontWeight: FontWeight,
    color: Color,
    lineHeight: androidx.compose.ui.unit.TextUnit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            offsetX = Random.nextFloat() * 6 - 3
            offsetY = Random.nextFloat() * 6 - 3
            delay(100)
        }
    }
    Box {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            textAlign = TextAlign.Center,
            lineHeight = lineHeight,
            modifier = Modifier.offset(offsetX.dp, offsetY.dp)
        )
    }
}
