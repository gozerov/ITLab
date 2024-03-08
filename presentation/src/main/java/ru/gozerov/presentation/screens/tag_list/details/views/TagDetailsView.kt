package ru.gozerov.presentation.screens.tag_list.details.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.gozerov.presentation.ui.theme.ITLabTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagDetailsView() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ITLabTheme.colors.primaryBackground
    ){ paddingValues ->

    }
}