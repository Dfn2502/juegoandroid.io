package mx.uacj.juego_ra.view_models.servicios

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

class ReproductorAudio(private val contexto: Context, private val loopear: Boolean = false){
    private var reproductor_media: MediaPlayer? = null
    private var audio_a_repdroducir: Int? = null


    fun cargar_audio(id_audio: Int){
        audio_a_repdroducir = id_audio

        if(reproductor_media == null){
            reproductor_media = MediaPlayer.create(contexto, audio_a_repdroducir!!)

            reproductor_media?.setOnCompletionListener {
                Log.v("AUDIO", "FINALIZO LA EJECUCION")
                if(loopear) {
                    hacer_loop()
                }
            }

            reproductor_media?.setOnPreparedListener {
                Log.v("AUDIO", "SE cargo el audio")
                reproductor_media?.start()

            }
        }
    }

    fun detener(){
        reproductor_media?.stop()
        reproductor_media?.release()
        reproductor_media = null
    }

    fun hacer_loop(){
        reproductor_media?.stop()
        reproductor_media?.release()
        reproductor_media = null
        cargar_audio(audio_a_repdroducir!!)
    }
}