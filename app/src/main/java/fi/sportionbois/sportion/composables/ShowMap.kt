package fi.sportionbois.sportion.composables

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import fi.sportionbois.sportion.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

@Composable
fun ShowMap(geoList: MutableList<LonLat>) {
    val map = ComposeMap()
    var mapInitialized by remember(map) { mutableStateOf(false) }
    if (!mapInitialized) {
        map.setTileSource(TileSourceFactory.MAPNIK)
        mapInitialized = true
    }
    //val marker = Marker(map)
    AndroidView(
        { map }, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        it.controller.setZoom(15.0)
        Log.d("why", "ehy")
        if (geoList.count() > 0) {
            Log.d("CENTERGEO", geoList[0].lat.toDouble().toString())
            it.controller.setCenter(GeoPoint(geoList[0].lat.toDouble(), geoList[0].lon.toDouble()))
        }
        val myPath = Polyline()
        myPath.color = Color.RED
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