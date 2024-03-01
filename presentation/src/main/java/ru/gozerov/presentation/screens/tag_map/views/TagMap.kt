package ru.gozerov.presentation.screens.tag_map.views

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsControllerCompat
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.ITLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagMap(contentPadding: PaddingValues) {
    var mapView: MapView? = null
    DisposableEffect(key1 = LocalLifecycleOwner.current) {
        onDispose {
            mapView?.onStop()
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ITLabTheme.colors.primaryBackground)
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { context ->
                    MapView(context).apply {
                        mapView = this
                        this.onStart()
                    }
                }
            )
            Column {
                FilledIconButton(
                    modifier = Modifier.padding(end = 8.dp, bottom = 4.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = ITLabTheme.colors.primaryBackground.copy(
                            0.7f
                        )
                    ),
                    onClick = {
                        mapView?.run {
                            mapWindow.map.move(
                                CameraPosition(
                                    mapWindow.map.cameraPosition.target,
                                    mapWindow.map.cameraPosition.zoom + 1, 0.0f, 0.0f
                                ),
                                Animation(Animation.Type.SMOOTH, 0.2f),
                                null
                            )
                        }

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
                FilledIconButton(
                    modifier = Modifier.padding(end = 8.dp, top = 4.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = ITLabTheme.colors.primaryBackground.copy(
                            0.7f
                        )
                    ),
                    onClick = {
                        mapView?.run {
                            mapWindow.map.move(
                                CameraPosition(
                                    mapWindow.map.cameraPosition.target,
                                    mapWindow.map.cameraPosition.zoom - 1, 0.0f, 0.0f
                                ),
                                Animation(Animation.Type.SMOOTH, 0.2f),
                                null
                            )
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove_24),
                        contentDescription = null
                    )
                }
            }
        }

    }
}