package ru.gozerov.presentation.screens.tag_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.geometry.Point
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.screens.tag_map.models.TagMapIntent
import ru.gozerov.presentation.screens.tag_map.models.TagMapViewState
import ru.gozerov.presentation.screens.tag_map.views.TagMap

@Composable
fun TagMapScreen(
    viewModel: TagMapViewModel
) {
    SetupSystemBars(statusBarColor = Color.Transparent)
    val snackbarScopeState = remember { SnackbarHostState() }

    val viewState = viewModel.viewState.collectAsState()
    val tagListState: MutableState<List<Tag>> = remember { mutableStateOf(emptyList()) }
    val pickedTag: MutableState<Tag?> = remember { mutableStateOf(null) }

    val moveCameraToUserState: MutableState<Point?> = remember { mutableStateOf(null) }
    val isSetupSystemBarsNeeded = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->

        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(TagMapIntent.LoadTags)
        }

        TagMap(
            contentPadding = contentPadding,
            tagState = tagListState,
            pickedTag = pickedTag,
            moveCameraToUserState = moveCameraToUserState,
            isSetupSystemBarsNeeded = isSetupSystemBarsNeeded,
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

            is TagMapViewState.Error -> {}
        }
    }
}

/*if (openTagDetails) {
    AlertDialogExample(
        onDismissRequest = {  },
        onConfirmation = {  },
        dialogTitle = "Title",
        dialogText = "Description"
    )
}*/
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}