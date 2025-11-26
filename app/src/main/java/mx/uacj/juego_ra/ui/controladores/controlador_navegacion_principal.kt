package mx.uacj.juego_ra.ui.controladores

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.uacj.juego_ra.ui.pantallas.PantallaContexto
import mx.uacj.juego_ra.ui.pantallas.PantallaInicio
import mx.uacj.juego_ra.ui.pantallas.PantallaPermisos // ¡Importa la nueva pantalla!
import mx.uacj.juego_ra.ui.pantallas.Principal

object Rutas {
    const val PERMISOS = "pantalla_permisos" // ¡Añade la ruta de permisos!
    const val INICIO = "pantalla_inicio"
    const val CONTEXTO = "pantalla_contexto"
    const val PRINCIPAL = "pantalla_principal"
}

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.PERMISOS // ¡La app ahora empieza aquí!
    ) {
        composable(Rutas.PERMISOS) {
            PantallaPermisos(
                onPermisosConcedidos = {
                    // Cuando los permisos se aceptan, navega a la pantalla de inicio
                    // y elimina la pantalla de permisos del historial para no poder volver.
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.PERMISOS) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.INICIO) {
            PantallaInicio(
                onIniciarClick = { navController.navigate(Rutas.CONTEXTO) }
            )
        }

        composable(Rutas.CONTEXTO) {
            PantallaContexto(
                onContinuarClick = { navController.navigate(Rutas.PRINCIPAL) }
            )
        }

        composable(Rutas.PRINCIPAL) {
            Principal(navegador = navController)
        }
    }
}
