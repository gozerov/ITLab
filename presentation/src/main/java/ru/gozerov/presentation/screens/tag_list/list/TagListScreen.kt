package ru.gozerov.presentation.screens.tag_list.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.navigation.Screen
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.screens.tag_list.list.models.TagListIntent
import ru.gozerov.presentation.screens.tag_list.list.models.TagListViewState
import ru.gozerov.presentation.screens.tag_list.list.views.TagListView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagListScreen(
    navController: NavController,
    viewModel: TagListViewModel,
    paddingValues: PaddingValues
) {
    SetupSystemBars()
    val tagState = remember { mutableStateOf<List<Tag>>(emptyList()) }
    val snackbarScopeState = remember { SnackbarHostState() }
    val viewState = viewModel.viewState.collectAsState().value

    LaunchedEffect(key1 = null) {
        viewModel.handleIntent(TagListIntent.LoadTags)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->

        TagListView(tagList = tagState.value) { tag ->
            navController.currentBackStackEntry?.savedStateHandle?.apply {
                set("tag", tag)
            }
            navController.navigate(Screen.TagDetails.route)
        }
    }

    when (viewState) {
        is TagListViewState.None -> {}
        is TagListViewState.TagList -> {
            tagState.value = viewState.tags
        }

        is TagListViewState.Error -> {

        }
    }
}