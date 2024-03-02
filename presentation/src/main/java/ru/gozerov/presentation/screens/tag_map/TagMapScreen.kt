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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.screens.tag_map.models.TagMapIntent
import ru.gozerov.presentation.screens.tag_map.models.TagMapViewState
import ru.gozerov.presentation.screens.tag_map.views.TagMap

@Composable
fun TagMapScreen(
    viewModel: TagMapViewModel
) {
    val snackbarScopeState = remember { SnackbarHostState() }
    SetupSystemBars(statusBarColor = Color.Transparent)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->
        val viewState = viewModel.viewState.collectAsState()
        val tagState: MutableState<List<Tag>> = remember { mutableStateOf(emptyList()) }

        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(TagMapIntent.LoadTags)
        }

        TagMap(contentPadding, tagState)

        when (viewState.value) {
            is TagMapViewState.None -> {}
            is TagMapViewState.TagsOnMap -> {
                tagState.value = (viewState.value as TagMapViewState.TagsOnMap).tags
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