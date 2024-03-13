package ru.gozerov.presentation.screens.tag_map.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.gozerov.domain.models.login.LoginMode
import ru.gozerov.domain.models.tags.CreateTagData
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.shared.RequestCoarseLocation
import ru.gozerov.presentation.screens.shared.RequestFineLocation
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.screens.tag_map.models.TagData
import ru.gozerov.presentation.ui.theme.ITLabTheme
import ru.gozerov.presentation.utils.getLocation
import ru.gozerov.presentation.utils.moveCamera

private val mapObjectTapListener = MapObjectTapListener { mapObject, _ ->
    val (pickedState, tag) = mapObject.userData as TagData
    pickedState.value = tag
    true
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagMapView(
    contentPadding: PaddingValues,
    mapViewState: MutableState<MapView?>,
    tagList: List<Tag>,
    loginModeState: MutableState<LoginMode>,
    onLikeClicked: (tag: Tag, isLikedState: MutableState<Boolean>) -> Unit,
    pickedTag: MutableState<Tag?>,
    moveCameraToUserState: MutableState<Point?>,
    isSetupSystemBarsNeeded: MutableState<Boolean>,
    isPointAdding: MutableState<Boolean>,
    addingPoint: MutableState<PlacemarkMapObject?>,
    onPickedTagDismiss: () -> Unit,
    onConfirmCreatingTag: (createTagData: CreateTagData) -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val tagBottomSheetState = rememberModalBottomSheetState()
    val isDarkMode = isSystemInDarkTheme()

    LaunchedEffect(key1 = null) {
        mapViewState.value?.mapWindow?.map?.isNightModeEnabled = isDarkMode
    }

    tagList.forEach { tag ->
        mapViewState.value?.mapWindow?.map?.mapObjects?.addPlacemark {
            it.geometry = Point(tag.latitude, tag.longitude)
            it.setIcon(ImageProvider.fromResource(context, R.drawable.ic_pin))
            it.userData = TagData(pickedTag, tag)
            it.addTapListener(mapObjectTapListener)
        }
    }

    val inputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            if (isPointAdding.value) {
                val placemarkMapObject = map.mapObjects.addPlacemark {
                    it.geometry = Point(point.latitude, point.longitude)
                    it.setIcon(ImageProvider.fromResource(context, R.drawable.ic_pin))
                }
                addingPoint.value = placemarkMapObject
            }
        }

        override fun onMapLongTap(p0: Map, p1: Point) {}
    }

    if (isSetupSystemBarsNeeded.value)
        SetupSystemBars(Color.Transparent)

    SetupMap(moveCameraToUserState, mapViewState, fusedLocationClient, inputListener)
    SetupSystemBars(Color.Transparent)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = contentPadding.calculateBottomPadding())
            .background(ITLabTheme.colors.primaryBackground)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { context ->
                    MapView(context).apply {
                        mapViewState.value = this
                        this.onStart()
                        mapViewState.value?.mapWindow?.map?.addInputListener(inputListener)
                    }
                }
            )
            Column {
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(100))
                        .alpha(0.9f)
                        .background(ITLabTheme.colors.primaryBackground),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    DefaultMapButton(
                        modifier = Modifier.padding(bottom = 4.dp),
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
                        modifier = Modifier.padding(top = 4.dp),
                        iconId = R.drawable.ic_remove_24
                    ) {
                        mapViewState.value?.run {
                            moveCamera(
                                point = mapWindow.map.cameraPosition.target,
                                zoom = mapWindow.map.cameraPosition.zoom - 1
                            )
                        }
                    }
                }

                DefaultMapButton(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 4.dp)
                        .size(48.dp),
                    iconId = R.drawable.ic_location_24
                ) {
                    fusedLocationClient.getLocation { loc ->
                        moveCameraToUserState.value = Point(loc.latitude, loc.longitude)
                    }
                }

                DefaultMapButton(
                    modifier = Modifier
                        .padding(end = 4.dp, top = 8.dp)
                        .size(48.dp),
                    iconId = R.drawable.ic_add_location_24,
                    tint = if (isPointAdding.value) ITLabTheme.colors.tintColor else ITLabTheme.colors.primaryText
                ) {
                    isPointAdding.value = !isPointAdding.value
                }
            }
            pickedTag.value?.let {
                isSetupSystemBarsNeeded.value = false
                TagDetailsDialog(
                    loginModeState = loginModeState,
                    tagState = pickedTag,
                    tagBottomSheetState = tagBottomSheetState,
                    onLikeClicked = onLikeClicked,
                    onDismiss = onPickedTagDismiss
                )
            }
            addingPoint.value?.let { placemarkMapObject ->
                CreateTagDialog(
                    onDismiss = { isConfirmed ->
                        if (!isConfirmed)
                            mapViewState.value?.mapWindow?.map?.mapObjects?.remove(
                                placemarkMapObject
                            )
                        addingPoint.value = null
                        isPointAdding.value = false
                    },
                    onConfirm = { description, imageBody ->
                        val point = placemarkMapObject.geometry
                        onConfirmCreatingTag(
                            CreateTagData(
                                latitude = point.latitude,
                                longitude = point.longitude,
                                description = description,
                                imageUri = imageBody
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SetupMap(
    moveCameraToUserState: MutableState<Point?>,
    mapViewState: MutableState<MapView?>,
    fusedLocationClient: FusedLocationProviderClient,
    inputListener: InputListener
) {
    moveCameraToUserState.value?.let { point ->
        mapViewState.value.moveCamera(point = point, zoom = 16f)
        moveCameraToUserState.value = null
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
            mapViewState.value?.mapWindow?.map?.removeInputListener(inputListener)
        }
    }
}