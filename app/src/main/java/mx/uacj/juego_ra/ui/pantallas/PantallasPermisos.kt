package mx.uacj.juego_ra.ui.pantallas


import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PantallaPermisos(
    onPermisosConcedidos: () -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        )
    )

    if (permissionsState.allPermissionsGranted) {
        onPermisosConcedidos()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Permisos Requeridos",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Para vivir la experiencia completa, esta aplicación necesita acceso a tu ubicación y a la cámara. Por favor, concede los permisos cuando se te soliciten.",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                permissionsState.launchMultiplePermissionRequest()
            }) {
                Text("Conceder Permisos")
            }
            LaunchedEffect(permissionsState.allPermissionsGranted) {
                if (permissionsState.allPermissionsGranted) {
                    onPermisosConcedidos()
                }
            }
        }
    }
}
