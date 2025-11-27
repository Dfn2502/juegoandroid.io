package mx.uacj.juego_ra.ui.atomos

import android.content.Context
import android.graphics.BitmapFactory
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import mx.uacj.juego_ra.R
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.SymbolLayer
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection
import org.maplibre.geojson.Point

// IDs para las fuentes y capas. Es una buena práctica definirlas como constantes.
private const val SOURCE_ID_JUGADOR = "source-jugador"
private const val LAYER_ID_JUGADOR = "layer-jugador"
private const val ICON_ID_JUGADOR = "icon-jugador"

@Composable
fun MapaMapLibre(
    modifier: Modifier,
    ubicacionUsuario: Location?
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    // DisposableEffect se encarga de limpiar recursos cuando el composable se va.
    DisposableEffect(Unit) {
        onDispose {
            mapView.onDestroy()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            // Se ejecuta solo la primera vez para crear e inicializar el mapa.
            mapView.apply {
                getMapAsync { map ->
                    configurarEstiloYCapas(context, map)
                }
            }
        },
        update = {
            // Se ejecuta cada vez que el estado `ubicacionUsuario` cambia.
            it.getMapAsync { map ->
                actualizarPosicionJugadorYCamara(map, ubicacionUsuario)
            }
        }
    )
}

private fun configurarEstiloYCapas(context: Context, map: MapLibreMap) {
    MapLibre.getInstance(context)

    val styleUrl = "https://api.maptiler.com/maps/basic-v2/style.json?key=kdm9d7JoeZLyRF6J46LU"
    map.setStyle(styleUrl) { style ->
        // Posición inicial por defecto
        val posJuarez = LatLng(31.73, -106.48)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(posJuarez, 19.0))

        // 1. Añadimos el icono del jugador al estilo del mapa
        style.addImage(
            ICON_ID_JUGADOR,
            BitmapFactory.decodeResource(context.resources, R.drawable.jugador)
        )
        style.addSource(GeoJsonSource(SOURCE_ID_JUGADOR))
        style.addLayer(
            SymbolLayer(LAYER_ID_JUGADOR, SOURCE_ID_JUGADOR)
                .withProperties(
                    PropertyFactory.iconImage(ICON_ID_JUGADOR),
                    PropertyFactory.iconAllowOverlap(true),
                    PropertyFactory.iconSize(0.2f)
                )
        )
    }
}

private fun actualizarPosicionJugadorYCamara(map: MapLibreMap, ubicacionUsuario: Location?) {
    val style = map.style
    if (style == null || ubicacionUsuario == null) {
        return // Si el estilo no está listo o no hay ubicación, no hacemos nada.
    }

    // Obtenemos la fuente (source) que creamos antes.
    val jugadorSource = style.getSourceAs<GeoJsonSource>(SOURCE_ID_JUGADOR)

    jugadorSource?.setGeoJson(
        // Creamos una nueva FeatureCollection con la ubicación actual del jugador.
        FeatureCollection.fromFeatures(
            arrayOf(
                Feature.fromGeometry(
                    Point.fromLngLat(ubicacionUsuario.longitude, ubicacionUsuario.latitude)
                )
            )
        )
    )

    // Movemos la cámara para centrarla en el jugador.
    map.animateCamera(
        CameraUpdateFactory.newLatLng(LatLng(ubicacionUsuario.latitude, ubicacionUsuario.longitude)),
        1000 // Animación de 1 segundo
    )
}
