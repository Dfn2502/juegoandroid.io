package mx.uacj.juego_ra.ui.organismos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mx.uacj.juego_ra.modelos.InformacionInteractiva
// --- INICIO DE LA CORRECCIÓN #1: Importar el objeto Rutas ---
import mx.uacj.juego_ra.ui.controladores.Rutas
// --- FIN DE LA CORRECCIÓN #1 ---
import mx.uacj.juego_ra.view_models.ControladorGeneral

@Composable
fun InformacionInteractivaVista(
    navegador: NavHostController,
    informacion_interactiva: InformacionInteractiva,
    controlador_general: ControladorGeneral
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = informacion_interactiva.texto.toString(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                navegador.navigate(Rutas.SCANNER_QR)
            }
        ) {
            Text("Escanear Código")
        }
    }
}