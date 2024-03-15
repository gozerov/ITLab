package ru.gozerov.presentation.screens.shared

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocation(onSuccess: () -> Unit) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    if (!permissionState.allPermissionsGranted) {
        LaunchedEffect(key1 = null) {
            permissionState.launchMultiplePermissionRequest()
        }
    } else {
        LaunchedEffect(key1 = null) {
            onSuccess()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestImageStorage(onSuccess: () -> Unit) {
    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    if (!permissionState.status.isGranted) {
        SideEffect {
            permissionState.launchPermissionRequest()
        }
    } else {
        SideEffect {
            onSuccess()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotifications() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permissionState =
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        if (!permissionState.status.isGranted) {
            LaunchedEffect(key1 = null) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}