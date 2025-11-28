package mx.uacj.juego_ra.repositorios.estaticos

import android.location.Location
import mx.uacj.juego_ra.modelos.InformacionInteractiva
import mx.uacj.juego_ra.modelos.Pista
import mx.uacj.juego_ra.ui.controladores.Rutas

object RepositorioPruebas {


    var _pistas = listOf(
        Pista(
            nombre = "Evento 1 - El Aviso",
            ubicacion = Location("proveedor").apply {
                latitude = 31.742637
                longitude = -106.433608
            },
            distancia_minima = 15f,
            distancia_maxima = 150f,
            cuerpo = InformacionInteractiva(
                texto = "Algo pasa en DDMI y tu lo tienes que descubrir, estas listo?. " +
                        "Cinco fueron elegidos. Cinco fallaron. Encuentra sus notas antes de que te encuentren a ti. /n La casa de DDMI fue testigo de lo que paso",
                ruta = Rutas.PANTALLA_DIALOGO_PISTA
            )
        ),

        Pista(
            nombre = "Evento 2 - El fantasma",
            ubicacion = Location("proveedor").apply {
                latitude = 31.7421699
                longitude = -106.4326037
            },
            distancia_minima = 15f,
            distancia_maxima = 150f,
            cuerpo = InformacionInteractiva(
                texto = "El sensor de movimiento activa interferencia cerca de un aula vacía. En RA aparece Ana, alumna proyectada: “No queríamos reprobar, queríamos entender…”\nEncuentra la nota con símbolos y coordenadas hacia el siguiente edificio.",
                ruta = Rutas.EVENTO_FANTASMA
            )
        ),

        Pista(
            nombre = "Evento 3 — Lenguajes de programación",
            ubicacion = Location("proveedor").apply {
                latitude = 31.7434431
                longitude = -106.4322366
            },
            distancia_minima = 15f,
            distancia_maxima = 150f,
            cuerpo = InformacionInteractiva(
                texto = "Escanea el pizarrón lleno de binario. Inclina el teléfono para alinear los 0s y 1s. Audio de Luis: “El código se escribía solo… como si alguien más estuviera programando conmigo.”\nSe revela la carpeta digital: Reprobados/Notas.txt.",
                ruta = Rutas.EVENTO_PIZARRON
            )
        ),
        Pista(
            nombre = "Evento 4 — La biblioteca silenciosa",
            ubicacion = Location("proveedor").apply {
                latitude = 31.7430163
                longitude = -106.4331737
            },
            distancia_minima = 15f,
            distancia_maxima = 150f,
            cuerpo = InformacionInteractiva(
                texto = "Escanea la hoja marcada dentro del libro. El texto se transforma: “El conocimiento no es el problema. Es lo que el programa hace con tus pensamientos.”\nEl teléfono vibra y se marca el siguiente punto.",
                ruta = Rutas.PANTALLA_DIALOGO_PISTA
            )
        ),

        Pista(
            nombre = "Evento 5 — ¿Que paso?",
            ubicacion = Location("proveedor").apply {
                latitude = 31.7428811
                longitude = -106.4303225
            },
            distancia_minima = 15f,
            distancia_maxima = 150f,
            cuerpo = InformacionInteractiva(
                texto = "Susurros en los pasillos. En RA se leen mensajes escritos: “Dicen que uno sigue viniendo de noche.” “Los programas se ejecutan solos.”\nEscanea el símbolo oculto y escucha la conversación entre Sofía y Karla: “Él dijo que reprobar era parte del experimento.”",
                ruta = Rutas.EVENTO_FINAL
            )
        ),

    )
    private var indiceActual = 0

    val totalPistas: Int get() = _pistas.size

    fun obtenerPistaActual(): Pista? = _pistas.getOrNull(indiceActual)

    fun avanzarPista() {
        if (indiceActual < _pistas.size - 1) {
            indiceActual++
        }
    }

    fun reiniciarPistas() {
        indiceActual = 0
    }
}
