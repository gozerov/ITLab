package ru.gozerov.presentation.utils

import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

fun MapView?.moveCamera(point: Point, zoom: Float) {
    this?.mapWindow?.map?.run {
        this.move(CameraPosition(point, zoom, 0f, 0f), Animation(Animation.Type.SMOOTH, 0.2f), null)
    }
}
