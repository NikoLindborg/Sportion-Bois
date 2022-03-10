package fi.sportionbois.sportion.composables

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import fi.sportionbois.sportion.R
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

/**
 * Composables for showing and composing a map.
 *
 * Also has logic for drawing route to map as a Polyline
 **/

@Composable
fun ShowMap(geoList: MutableList<LonLat>) {
    val map = ComposeMap()
    var mapInitialized by remember(map) { mutableStateOf(false) }
    if (!mapInitialized) {
        map.setTileSource(TileSourceFactory.MAPNIK)
        mapInitialized = true
    }
    AndroidView(
        { map }, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        it.controller.setZoom(15.0)
        //  Sets the first GeoPoint as the centered value
        if (geoList.count() > 0) {
            it.controller.setCenter(GeoPoint(geoList[0].lat.toDouble(), geoList[0].lon.toDouble()))
        }
        val myPath = Polyline()
        myPath.color = Color.RED
        //  Receives the geopoints and adds them to the myPath Polyline which draws the route
        for (item in geoList) {
            myPath.addPoint(GeoPoint(item.lat.toDouble(), item.lon.toDouble()))
        }
        it.overlays.add(myPath)
    }
}


@Composable
fun ComposeMap(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }
    return mapView
}