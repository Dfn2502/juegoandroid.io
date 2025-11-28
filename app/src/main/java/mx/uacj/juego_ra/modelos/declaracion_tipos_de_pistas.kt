package mx.uacj.juego_ra.modelos


public interface PistaGenerica{
    val tipo: TiposDePistas
    val texto: String?
}

data class Informacion(
        override val tipo: TiposDePistas = TiposDePistas.texto,
        override val texto: String,
        val imagen: Int? = null
) : PistaGenerica

data class Boton(
    val texto: String,
    val direccion: String
)

data class InformacionInteractiva(
    override val tipo: TiposDePistas = TiposDePistas.interactiva,
    override val texto: String?,
    val ruta: String
) : PistaGenerica
