package ru.gozerov.presentation.screens.shared

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCoarseLocation() {
    val coarseLocationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)

    if (!coarseLocationPermissionState.status.isGranted)
        LaunchedEffect(key1 = null) {
            coarseLocationPermissionState.launchPermissionRequest()
        }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestFineLocation(onSuccess: () -> Unit) {
    val fineLocationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    if (!fineLocationPermissionState.status.isGranted)
        LaunchedEffect(key1 = null) {
            fineLocationPermissionState.launchPermissionRequest()
        }
    if (fineLocationPermissionState.status is PermissionStatus.Granted) {
        LaunchedEffect(key1 = null) {
            onSuccess()
        }
    }
}