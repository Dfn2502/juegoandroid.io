package mx.uacj.juego_ra

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.maplibre.android.MapLibre
@HiltAndroidApp
class Aplicacion: Application(){
    override fun onCreate() {
        super.onCreate()
        MapLibre.getInstance(this)
    }
}