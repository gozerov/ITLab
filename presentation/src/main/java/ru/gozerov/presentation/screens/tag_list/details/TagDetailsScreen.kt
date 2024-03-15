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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.gozerov.domain.models.login.LoginMode
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

    val isSubscribedState = remember { mutableStateOf<Boolean?>(null) }
    val loginModeState: MutableState<LoginMode> = remember { mutableStateOf(LoginMode.GUEST) }
    val viewState = viewModel.viewState.collectAsState().value
    var isTagLiked: Boolean by remember { mutableStateOf(tag.isLiked) }

    LaunchedEffect(key1 = null) {
        viewModel.handleIntent(TagDetailsIntent.GetLoginMode)
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
                isSubscribedState = isSubscribedState,
                onTagClick = {
                    if (loginModeState.value == LoginMode.LOGGED) {
                        if (!it.isLiked)
                            viewModel.handleIntent(TagDetailsIntent.LikeTag(it.id))
                        else
                            viewModel.handleIntent(TagDetailsIntent.UnlikeTag(it))
                        isTagLiked = !isTagLiked
                    }
                },
                onDeleteTagClick = {
                    viewModel.handleIntent(TagDetailsIntent.DeleteTag(it.id))
                },
                onSubscribeAuthor = { username ->
                    isSubscribedState.value?.let { isSubscribed ->
                        viewModel.handleIntent(
                            TagDetailsIntent.SubscribeOnAuthor(
                                username,
                                isSubscribed
                            )
                        )
                    }
                }
            )

        }

    }
    when (viewState) {
        is TagDetailsViewState.None -> {}
        is TagDetailsViewState.UpdateLoginMode -> {
            loginModeState.value = viewState.loginMode
        }

        is TagDetailsViewState.UpdatedTag -> {
            currentTagDetails.value = viewState.tagDetails
            isSubscribedState.value = viewState.tagDetails.isSubscribed
        }

        is TagDetailsViewState.UpdateSubscription -> {
            isSubscribedState.value = viewState.isSubscribed
        }

        is TagDetailsViewState.TagHasBeenDeleted -> {
            navController.popBackStack()
        }

        is TagDetailsViewState.Error -> {

        }
    }

}