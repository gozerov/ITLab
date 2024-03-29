package ru.gozerov.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.navigation.NavHostContainer
import ru.gozerov.presentation.screens.shared.RequestNotifications
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.ui.theme.ITLabTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val viewState = viewModel.viewState.collectAsState()
            val navController = rememberNavController()
            RequestNotifications()

            ITLabTheme {
                SetupSystemBars()
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        containerColor = ITLabTheme.colors.primaryBackground
                    ) { paddingValues ->
                        viewState.value?.let {
                            NavHostContainer(
                                navController = navController,
                                padding = paddingValues,
                                it
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}