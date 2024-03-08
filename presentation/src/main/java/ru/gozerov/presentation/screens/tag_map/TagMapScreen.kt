package ru.gozerov.presentation.screens.tag_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.screens.tag_map.models.TagMapIntent
import ru.gozerov.presentation.screens.tag_map.models.TagMapViewState
import ru.gozerov.presentation.screens.tag_map.views.TagMapView

@Composable
fun TagMapScreen(
    viewModel: TagMapViewModel
) {
    SetupSystemBars(statusBarColor = Color.Transparent)
    val snackbarScopeState = remember { SnackbarHostState() }

    val viewState = viewModel.viewState.collectAsState()
    val mapViewState: MutableState<MapView?> = remember {
        mutableStateOf(null)
    }
    val tagListState: MutableState<List<Tag>> = remember { mutableStateOf(emptyList()) }
    val pickedTag: MutableState<Tag?> = remember { mutableStateOf(null) }

    val moveCameraToUserState: MutableState<Point?> = remember { mutableStateOf(null) }
    val isSetupSystemBarsNeeded = remember { mutableStateOf(false) }
    val isPointAdding = remember { mutableStateOf(false) }
    val addingPoint: MutableState<PlacemarkMapObject?> = remember { mutableStateOf(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->

        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(TagMapIntent.LoadTags)
        }

        TagMapView(
            contentPadding = contentPadding,
            mapViewState = mapViewState,
            tagList = tagListState.value,
            pickedTag = pickedTag,
            moveCameraToUserState = moveCameraToUserState,
            isSetupSystemBarsNeeded = isSetupSystemBarsNeeded,
            isPointAdding = isPointAdding,
            addingPoint = addingPoint,
            onPickedTagDismiss = {
                moveCameraToUserState.value = null
                pickedTag.value = null
                isSetupSystemBarsNeeded.value = true
                viewModel.handleIntent(TagMapIntent.LoadTags)
            },
            onLikeClicked = { tag, isLikedState ->
                if (!isLikedState.value)
                    viewModel.handleIntent(TagMapIntent.LikeTag(tag))
                else
                    viewModel.handleIntent(TagMapIntent.UnlikeTag(tag))
                isLikedState.value = !isLikedState.value
            },
            onConfirmCreatingTag = {
                viewModel.handleIntent(TagMapIntent.CreateTag(it))
            }
        )

        when (viewState.value) {
            is TagMapViewState.None -> {}
            is TagMapViewState.TagsOnMap -> {
                tagListState.value = (viewState.value as TagMapViewState.TagsOnMap).tags
            }

            is TagMapViewState.UpdateChosenTag -> {
                pickedTag.value = (viewState.value as TagMapViewState.UpdateChosenTag).tag
            }

            is TagMapViewState.Error -> {
                addingPoint.value?.let { placemarkObject ->
                    mapViewState.value?.mapWindow?.map?.mapObjects?.remove(placemarkObject)
                }
            }
        }
    }
}