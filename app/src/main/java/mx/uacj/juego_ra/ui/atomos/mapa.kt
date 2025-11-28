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

    DisposableEffect(Unit) {
        onDispose {
            mapView.onDestroy()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            mapView.apply {
                getMapAsync { map ->
                    configurarEstiloYCapas(context, map)
                }
            }
        },
        update = {
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
        val posJuarez = LatLng(31.73, -106.48)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(posJuarez, 19.0))

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
        return
    }

    val jugadorSource = style.getSourceAs<GeoJsonSource>(SOURCE_ID_JUGADOR)

    jugadorSource?.setGeoJson(
        FeatureCollection.fromFeatures(
            arrayOf(
                Feature.fromGeometry(
                    Point.fromLngLat(ubicacionUsuario.longitude, ubicacionUsuario.latitude)
                )
            )
        )
    )

    map.animateCamera(
        CameraUpdateFactory.newLatLng(LatLng(ubicacionUsuario.latitude, ubicacionUsuario.longitude)),
        1000
    )
}
