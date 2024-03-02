package ru.gozerov.presentation.screens.tag_map.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.shared.RequestCoarseLocation
import ru.gozerov.presentation.screens.shared.RequestFineLocation
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.screens.tag_map.models.TagData
import ru.gozerov.presentation.ui.theme.ITLabTheme
import ru.gozerov.presentation.utils.getLocation
import ru.gozerov.presentation.utils.moveCamera

private val mapObjectTapListener = object : MapObjectTapListener {
    override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean{
        val (pickedState, tag) = mapObject.userData as TagData
        pickedState.value = tag
        return true
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagMap(
    contentPadding: PaddingValues,
    tagState: MutableState<List<Tag>>
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val mapViewState: MutableState<MapView?> = remember {
        mutableStateOf(null)
    }
    val moveCameraToUserState: MutableState<Point?> = remember { mutableStateOf(null) }

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    var isSetupSystemBarsNeeded by remember { mutableStateOf(false) }
    val pickedTag: MutableState<Tag?> = remember { mutableStateOf(null) }
    val tagBottomSheetState = rememberModalBottomSheetState()

    tagState.value.forEach { tag ->
        mapViewState.value?.mapWindow?.map?.mapObjects?.addPlacemark {
            it.geometry = Point(tag.latitude, tag.longitude)
            it.setIcon(ImageProvider.fromResource(context, R.drawable.ic_pin))
            it.userData = TagData(pickedTag, tag)
            it.addTapListener(mapObjectTapListener)
        }
    }

    if (isSetupSystemBarsNeeded)
        SetupSystemBars(Color.Transparent, true)

    SetupMap(moveCameraToUserState, mapViewState, fusedLocationClient)
    SetupSystemBars(Color.Transparent, true)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(ITLabTheme.colors.primaryBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateTopPadding()),
                factory = { context ->
                    MapView(context).apply {
                        mapViewState.value = this
                        this.onStart()
                    }
                }
            )
            Column {
                DefaultMapButton(
                    modifier = Modifier.padding(end = 8.dp, bottom = 4.dp),
                    iconId = R.drawable.ic_add_24
                ) {
                    mapViewState.value?.run {
                        moveCamera(
                            point = mapWindow.map.cameraPosition.target,
                            zoom = mapWindow.map.cameraPosition.zoom + 1
                        )
                    }
                }

                DefaultMapButton(
                    modifier = Modifier.padding(end = 8.dp, top = 4.dp),
                    iconId = R.drawable.ic_remove_24
                ) {
                    mapViewState.value?.run {
                        moveCamera(
                            point = mapWindow.map.cameraPosition.target,
                            zoom = mapWindow.map.cameraPosition.zoom - 1
                        )
                    }
                }

                DefaultMapButton(
                    modifier = Modifier.padding(end = 8.dp, top = 4.dp),
                    iconId = R.drawable.ic_location_24
                ) {
                    fusedLocationClient.getLocation { loc ->
                        moveCameraToUserState.value = Point(loc.latitude, loc.longitude)
                    }
                }
            }
            pickedTag.value?.let {
                isSetupSystemBarsNeeded = false
                TagDetailsDialog(
                    tagState = pickedTag,
                    tagBottomSheetState = tagBottomSheetState,
                    coroutineScope = coroutineScope,
                    onDismiss = {
                        moveCameraToUserState.value = null
                        pickedTag.value = null
                        isSetupSystemBarsNeeded = true
                    }
                )
            }

        }

    }
}

@Composable
fun SetupMap(
    moveCameraToUserState: MutableState<Point?>,
    mapViewState: MutableState<MapView?>,
    fusedLocationClient: FusedLocationProviderClient
) {
    moveCameraToUserState.value?.let { point ->
        mapViewState.value.moveCamera(point = point, zoom = 16f)
    }

    RequestCoarseLocation()
    RequestFineLocation {
        fusedLocationClient.getLocation { loc ->
            if (moveCameraToUserState.value == null)
                moveCameraToUserState.value = Point(loc.latitude, loc.longitude)
        }
    }

    DisposableEffect(key1 = LocalLifecycleOwner.current) {
        onDispose {
            mapViewState.value?.onStop()
        }
    }
}