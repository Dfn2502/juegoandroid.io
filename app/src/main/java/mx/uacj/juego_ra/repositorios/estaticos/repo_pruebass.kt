package mx.uacj.juego_ra.repositorios.estaticos

import android.location.Location
import mx.uacj.juego_ra.R
import mx.uacj.juego_ra.modelos.Informacion
import mx.uacj.juego_ra.modelos.InformacionInteractiva
import mx.uacj.juego_ra.modelos.Pista
import mx.uacj.juego_ra.ui.controladores.Rutas
import kotlin.compareTo
import kotlin.inc

object RepositorioPruebas {


    var _pistas = listOf(
        // --- PISTA 1 CORREGIDA ---
        // Ahora es Interactiva y usa 'ruta' para navegar al siguiente paso (ej. el scanner)
        Pista(
            nombre = "Evento 1 - El Aviso",
            ubicacion = Location("proveedor").apply {
                latitude = 31.6290877
                longitude = -106.4596694
            },
            distancia_minima = 15f, // Añadido para consistencia
            distancia_maxima = 150f, // Añadido para consistencia
            cuerpo = InformacionInteractiva(
                texto = "Al llegar, el teléfono vibra y aparece el logo distorsionado de DDMI. " +
                        "/n Cinco fueron elegidos. Cinco fallaron. Encuentra sus notas antes de que te encuentren a ti.",
                ruta = Rutas.PANTALLA_DIALOGO_PISTA // <-- La ruta a la que debe navegar, por ejemplo
            )
        ),

        Pista(
            nombre = "Evento 2 - El fantasma",
            ubicacion = Location("proveedor").apply {
                latitude = 31.6290877
                longitude = -106.4596694
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
                latitude = 31.6289821
                longitude = -106.4598001
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
                latitude = 31.6290135
                longitude = -106.4597049
            },
            distancia_minima = 15f,
            distancia_maxima = 150f,
            cuerpo = InformacionInteractiva(
                texto = "Escanea la hoja marcada dentro del libro. El texto se transforma: “El conocimiento no es el problema. Es lo que el programa hace con tus pensamientos.”\nEl teléfono vibra y se marca el siguiente punto.",
                ruta = Rutas.PANTALLA_DIALOGO_PISTA
            )
        ),

        // --- PISTA 5 CORREGIDA ---
        Pista(
            nombre = "Evento 5 — ¿Que paso?",
            ubicacion = Location("proveedor").apply {
                latitude = 31.6290135
                longitude = -106.4597049
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
        // Si ya no hay más pistas, se queda en la última
    }

    fun reiniciarPistas() {
        indiceActual = 0
    }
}
