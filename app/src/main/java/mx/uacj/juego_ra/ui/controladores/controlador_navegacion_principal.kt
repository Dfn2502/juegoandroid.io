package mx.uacj.juego_ra.ui.controladores

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
// Importa el modelo para poder usarlo
import mx.uacj.juego_ra.modelos.InformacionInteractiva
import mx.uacj.juego_ra.ui.pantallas.EventoFantasmaPantalla
import mx.uacj.juego_ra.ui.pantallas.EventoFinalPantalla
import mx.uacj.juego_ra.ui.pantallas.EventoPizarronPantalla
import mx.uacj.juego_ra.ui.pantallas.PantallaContexto
import mx.uacj.juego_ra.ui.pantallas.PantallaDialogoPista
import mx.uacj.juego_ra.ui.pantallas.PantallaInicio
import mx.uacj.juego_ra.ui.pantallas.PantallaPermisos
import mx.uacj.juego_ra.ui.pantallas.PantallaResultadoScan
import mx.uacj.juego_ra.ui.pantallas.Principal
import mx.uacj.juego_ra.ui.pantallas.ScannerQRPantalla
import mx.uacj.juego_ra.view_models.ControladorGeneral
import mx.uacj.juego_ra.view_models.GestorUbicacion
import java.net.URLDecoder
import java.net.URLEncoder

object Rutas {
    const val PERMISOS = "pantalla_permisos"
    const val INICIO = "pantalla_inicio"
    const val CONTEXTO = "pantalla_contexto"
    const val JUEGO_GRAPH = "juego_graph"
    const val EVENTO_FINAL = "EventoFinal"
    const val PRINCIPAL = "Principal"
    const val SCANNER_QR = "pantalla_scanner_qr"
    const val PANTALLA_DIALOGO_PISTA = "PantallaDialogoPista"
    const val EVENTO_FANTASMA = "EventoFantasma"
    const val EVENTO_PIZARRON = "EventoPizarron"
    const val PANTALLA_RESULTADO_SCAN = "PantallaResultadoScan"
    const val PANTALLA_RESULTADO_SCAN_ARG = "texto" // Nombre del argumento
    val PANTALLA_RESULTADO_SCAN_RUTA_COMPLETA =
        "$PANTALLA_RESULTADO_SCAN/{$PANTALLA_RESULTADO_SCAN_ARG}"
}

@Composable
fun NavegacionApp(gestorUbicacion: GestorUbicacion) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.PERMISOS
    ) {

        // --- Pantallas iniciales ---
        composable(Rutas.PERMISOS) {
            PantallaPermisos(onPermisosConcedidos = {
                navController.navigate(Rutas.INICIO) {
                    popUpTo(Rutas.PERMISOS) { inclusive = true }
                }
            })
        }
        composable(Rutas.INICIO) {
            PantallaInicio(onIniciarClick = { navController.navigate(Rutas.CONTEXTO) })
        }
        composable(Rutas.CONTEXTO) {
            PantallaContexto(
                navegador = navController,
                gestorUbicacion = gestorUbicacion,
                onContinuarClick = {
                    navController.navigate(Rutas.JUEGO_GRAPH) {
                        popUpTo(Rutas.CONTEXTO) { inclusive = true }
                    }
                }
            )
        }

        // --- Grafo del juego ---
        navigation(
            startDestination = Rutas.PRINCIPAL,
            route = Rutas.JUEGO_GRAPH
        ) {

            composable(Rutas.PRINCIPAL) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Rutas.JUEGO_GRAPH)
                }
                val controladorGeneralCompartido: ControladorGeneral = hiltViewModel(parentEntry)
                Principal(
                    navegador = navController,
                    controlador_general = controladorGeneralCompartido,
                    gestor_ubicacion = gestorUbicacion
                )
            }
            composable(Rutas.EVENTO_FINAL) { backStackEntry ->
                // Obtenemos el ViewModel compartido del grafo del juego
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Rutas.JUEGO_GRAPH)
                }
                val controladorGeneralCompartido: ControladorGeneral = hiltViewModel(parentEntry)

                // Llamamos a la pantalla que creamos para el final del juego
                EventoFinalPantalla(
                    navegador = navController,
                    controlador_general = controladorGeneralCompartido
                )
            }

            // --- Pantalla de diálogo ---
            composable(Rutas.PANTALLA_DIALOGO_PISTA) { backStackEntry ->
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(Rutas.JUEGO_GRAPH) }
                val controladorGeneralCompartido: ControladorGeneral = hiltViewModel(parentEntry)
                val pistaActual by controladorGeneralCompartido.pista_actual.collectAsState()

                val textoDialogo: String
                val textoBoton: String
                val rutaSiguiente: String

                when (pistaActual?.nombre) {
                    "Evento 1 - El Aviso" -> {
                        textoDialogo = "Al llegar, el teléfono vibra y aparece el logo distorsionado de DDMI."
                        textoBoton = "Continuar"
                        rutaSiguiente = Rutas.SCANNER_QR
                    }
                    "Evento 4 — La biblioteca silenciosa" -> {
                        textoDialogo = "Escanea la hoja marcada dentro del libro. El texto se transforma."
                        textoBoton = "Continuar"
                        rutaSiguiente = Rutas.SCANNER_QR
                    }
                    else -> {
                        textoDialogo = (pistaActual?.cuerpo as? InformacionInteractiva)?.texto ?: "Cargando..."
                        textoBoton = "Continuar"
                        rutaSiguiente = (pistaActual?.cuerpo as? InformacionInteractiva)?.ruta ?: Rutas.PRINCIPAL
                    }
                }

                PantallaDialogoPista(
                    navegador = navController,
                    textoDialogo = textoDialogo,
                    textoBoton = textoBoton,
                    rutaSiguiente = rutaSiguiente
                )
            }

            // --- Scanner QR ---
            composable(Rutas.SCANNER_QR) { backStackEntry ->
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(Rutas.JUEGO_GRAPH) }
                val controladorGeneralCompartido: ControladorGeneral = hiltViewModel(parentEntry)
                val pistaActual by controladorGeneralCompartido.pista_actual.collectAsState()

                val onScanExitoso: () -> Unit = {
                    val textoPosterior = when (pistaActual?.nombre) {
                        "Evento 1 - El Aviso" -> "Cinco fueron elegidos. Cinco fallaron. Encuentra sus notas antes de que te encuentren a ti."
                        "Evento 4 — La biblioteca silenciosa" -> "El conocimiento no es el problema. Es lo que el programa hace con tus pensamientos."
                        else -> null
                    }

                    if (textoPosterior != null) {
                        val textoCodificado = URLEncoder.encode(textoPosterior, "UTF-8")
                        navController.navigate("${Rutas.PANTALLA_RESULTADO_SCAN}/$textoCodificado") {
                            popUpTo(Rutas.SCANNER_QR) { inclusive = true }
                        }
                    } else {
                        controladorGeneralCompartido.avanzarSiguientePista()
                        navController.navigate(Rutas.PRINCIPAL)
                    }
                }

                ScannerQRPantalla(
                    navegador = navController,
                    controlador_general = controladorGeneralCompartido,
                    onScanExitoso = onScanExitoso
                )
            }

            // --- Resultado Scan ---
            composable(
                route = Rutas.PANTALLA_RESULTADO_SCAN_RUTA_COMPLETA,
                arguments = listOf(navArgument(Rutas.PANTALLA_RESULTADO_SCAN_ARG) { type = NavType.StringType })
            ) { backStackEntry ->
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(Rutas.JUEGO_GRAPH) }
                val controladorGeneralCompartido: ControladorGeneral = hiltViewModel(parentEntry)

                val textoCodificado = backStackEntry.arguments?.getString(Rutas.PANTALLA_RESULTADO_SCAN_ARG) ?: ""
                val textoDecodificado = URLDecoder.decode(textoCodificado, "UTF-8")

                PantallaResultadoScan(
                    navegador = navController,
                    controlador_general = controladorGeneralCompartido,
                    textoAMostrar = textoDecodificado,
                    onCerrar = {
                        controladorGeneralCompartido.avanzarSiguientePista()
                        navController.navigate(Rutas.PRINCIPAL) {
                            popUpTo(Rutas.PANTALLA_RESULTADO_SCAN) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.EVENTO_FANTASMA) { backStackEntry ->
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(Rutas.JUEGO_GRAPH) }
                val controladorGeneralCompartido: ControladorGeneral = hiltViewModel(parentEntry)

                EventoFantasmaPantalla(
                    navegador = navController,
                    controladorGeneralCompartido
                )
            }

            composable(Rutas.EVENTO_PIZARRON) { backStackEntry ->
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry(Rutas.JUEGO_GRAPH) }
                val controladorGeneralCompartido: ControladorGeneral = hiltViewModel(parentEntry)

                EventoPizarronPantalla(
                    navegador = navController,
                    controlador_general = controladorGeneralCompartido
                )
            }
        }
    }
}