package mx.uacj.juego_ra.ui.organismos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import mx.uacj.juego_ra.modelos.Informacion

@Composable
fun InformacionVista(informacion_a_mostrar: Informacion){
    Column {
        Text(informacion_a_mostrar.texto)

        if(informacion_a_mostrar.imagen != null){
            Image(
                painter = painterResource(informacion_a_mostrar.imagen),
                contentDescription = "Imagen de la pista"
            )
        }
        /*
        informacion_a_mostrar.imagen?.let {
            Image(
                painter = painterResource(informacion_a_mostrar.imagen),
                contentDescription = "Imagen de la pista"
            )
        }
        */
    }
}