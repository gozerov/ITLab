package ru.gozerov.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.domain.usecases.login.PerformLogin
import ru.gozerov.domain.usecases.login.PerformRegister
import ru.gozerov.presentation.navigation.NavHostContainer
import ru.gozerov.presentation.ui.theme.ITLabTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var mapView: MapView? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)

        setContent {
            val navController = rememberNavController()
            ITLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold {
                        NavHostContainer(navController = navController, padding = it)
                    }
                }

            }

        }
    }

    /*AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            MapView(context).apply {
                                mapView = this
                            }
                        }
                    )*/
    override fun onStart() {
        super.onStart()
        /*MapKitFactory.getInstance().onStart()
        mapView?.onStart()*/
    }

    override fun onStop() {
        /*mapView?.onStop()
        MapKitFactory.getInstance().onStop()*/
        super.onStop()
    }

}