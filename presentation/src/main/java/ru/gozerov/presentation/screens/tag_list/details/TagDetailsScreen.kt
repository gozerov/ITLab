package ru.gozerov.presentation.screens.tag_list.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.domain.models.tags.TagDetails
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.screens.tag_list.details.models.TagDetailsIntent
import ru.gozerov.presentation.screens.tag_list.details.models.TagDetailsViewState
import ru.gozerov.presentation.screens.tag_list.details.views.TagDetailsView
import ru.gozerov.presentation.ui.theme.ITLabTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagDetailsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: TagDetailsViewModel,
    tag: Tag
) {
    SetupSystemBars()
    val currentTagDetails = remember { mutableStateOf<TagDetails?>(null) }
    val snackbarScopeState = remember { SnackbarHostState() }
    val viewState = viewModel.viewState.collectAsState().value
    var isTagLiked: Boolean by remember { mutableStateOf(tag.isLiked) }

    LaunchedEffect(key1 = null) {
        viewModel.handleIntent(TagDetailsIntent.LoadTag(tag.id))
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        containerColor = ITLabTheme.colors.primaryBackground,
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->
        val tagDetails = currentTagDetails.value
        if (tagDetails != null) {
            TagDetailsView(
                tagDetails = tagDetails,
                isTagLiked = isTagLiked,
                onTagClick =  {
                    if (!it.isLiked)
                        viewModel.handleIntent(TagDetailsIntent.LikeTag(it.id))
                    else
                        viewModel.handleIntent(TagDetailsIntent.UnlikeTag(it))
                    isTagLiked = !isTagLiked
                },
                onDeleteTagClick = {

                }
            )

        }

    }
    when (viewState) {
        is TagDetailsViewState.None -> { }
        is TagDetailsViewState.UpdatedTag -> {
            currentTagDetails.value = viewState.tagDetails
        }
        is TagDetailsViewState.TagHasBeenDeleted -> {

        }
        is TagDetailsViewState.Error -> {

        }
    }

}