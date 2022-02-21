package fi.sportionbois.sportion.composables

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

@Composable
fun ShowMap(lat: Double, lon: Double, address: String) {
    val map = ComposeMap()
    var mapInitialized by remember(map) { mutableStateOf(false) }
    if(!mapInitialized) {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(9.0)
        map.controller.setCenter(GeoPoint(60.17, 24.95))
        mapInitialized = true
    }
    AndroidView({ map }, modifier = Modifier.fillMaxHeight().fillMaxWidth())
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