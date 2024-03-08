package ru.gozerov.presentation.screens.tag_list.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.screens.tag_list.details.models.TagDetailsIntent
import ru.gozerov.presentation.screens.tag_list.details.models.TagDetailsViewState
import ru.gozerov.presentation.screens.tag_list.details.views.TagDetailsView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagDetailsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: TagDetailsViewModel,
    tag: Tag
) {
    SetupSystemBars()
    val currentTag = remember { mutableStateOf(tag) }
    val snackbarScopeState = remember { SnackbarHostState() }
    val viewState = viewModel.viewState.collectAsState().value
    var isTagLiked: Boolean by remember { mutableStateOf(currentTag.value.isLiked) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->
        TagDetailsView(
            tag = currentTag.value,
            isTagLiked = isTagLiked
        ) {
            if (!it.isLiked)
                viewModel.handleIntent(TagDetailsIntent.LikeTag(it.id))
            else
                viewModel.handleIntent(TagDetailsIntent.UnlikeTag(it))
            isTagLiked = !isTagLiked
        }
    }
    when (viewState) {
        is TagDetailsViewState.None -> { }
        is TagDetailsViewState.UpdatedTag -> {
            currentTag.value = viewState.tag
        }
        is TagDetailsViewState.TagHasBeenDeleted -> {

        }
        is TagDetailsViewState.Error -> {

        }
    }

}