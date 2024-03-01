package ru.gozerov.presentation.screens.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.gozerov.presentation.ui.theme.ITLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ITLabTheme.colors.tintColor
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Account")
        }
    }
}