package mx.uacj.juego_ra.ui.pantallas

import android.location.Location
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.uacj.juego_ra.R
import mx.uacj.juego_ra.modelos.Informacion
import mx.uacj.juego_ra.modelos.InformacionInteractiva
import mx.uacj.juego_ra.modelos.TiposDePistas
import mx.uacj.juego_ra.repositorios.estaticos.RepositorioPruebas
import mx.uacj.juego_ra.ui.organismos.InformacionInteractivaVista
import mx.uacj.juego_ra.ui.organismos.InformacionVista

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Principal(ubicacion: Location?, modificador: Modifier = Modifier){

    var mostrar_pantalla_generica by remember { mutableStateOf(true) }
    var mostrar_pista_cercana by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Las notas de los reprobados",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1C1C3C)
                )
            )
        },
        containerColor = Color(0xFFE6E0FA)

    ){innerPadding ->

        Column(
            modifier = modificador
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.mapa),
                contentDescription = "Mapa del campus",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.brujula),
                contentDescription = "Brujula",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            var pistaEncontrada = false

            for(pista in RepositorioPruebas.pistas){
                if(ubicacion == null){
                    break
                }

                var distancia_a_la_pista = ubicacion.distanceTo(pista.ubicacion)

                if(distancia_a_la_pista < pista.distancia_maxima) {
                    pistaEncontrada = true
                    mostrar_pantalla_generica = false
                    var nivel_de_distancia =
                        (distancia_a_la_pista * 100) / (pista.distancia_maxima - pista.distancia_minima)

                    Text(
                        "La pista es: ${pista.nombre}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF1C1C3C),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        "el nivel de la distancia a la pista es ${nivel_de_distancia}",
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )

                    if (nivel_de_distancia > 75) {
                        Text("Estas frio todavia")
                    } else if (nivel_de_distancia > 50) {
                        Text("Te estas acercando")
                    } else if (nivel_de_distancia > 25) {
                        Text("Muy cercas, sigue asi")
                    } else if (nivel_de_distancia < 20 && !mostrar_pista_cercana) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .background(Color(0xFF1C1C3C), shape = MaterialTheme.shapes.medium)
                                .clickable {
                                    mostrar_pista_cercana = true
                                }.padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Capturar pista cercana",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if(mostrar_pista_cercana) {
                        when (pista.cuerpo.tipo) {
                            TiposDePistas.texto -> {
                                InformacionVista(pista.cuerpo as Informacion)
                            }

                            TiposDePistas.interactiva -> {
                                InformacionInteractivaVista(pista.cuerpo as InformacionInteractiva)
                            }

                            TiposDePistas.camara -> {
                                TODO()
                            }

                            TiposDePistas.agitar_telefono -> {
                                TODO()
                            }
                        }
                    }

                }
            }
        }

    }




    if(mostrar_pantalla_generica){
        Column(modificador) {
            Text(
                "NO te encuentras cercas de alguna pista por el momento ",
                color = Color(0xFF1C1C3C),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
                )
            Text(
                "Por favor sigue explorando ",
                color = Color.DarkGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)

                )
        }

    }

}
