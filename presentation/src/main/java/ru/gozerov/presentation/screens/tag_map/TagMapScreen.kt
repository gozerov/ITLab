package ru.gozerov.presentation.screens.tag_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ru.gozerov.presentation.screens.tag_map.views.TagMap
import ru.gozerov.presentation.ui.theme.ITLabTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagMapScreen() {
    val snackbarScopeState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->
        TagMap(contentPadding)
    }
}