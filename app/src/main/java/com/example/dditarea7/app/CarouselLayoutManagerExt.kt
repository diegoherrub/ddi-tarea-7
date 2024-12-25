package com.example.dditarea7.app

import android.view.View
import com.google.android.material.carousel.CarouselLayoutManager
import kotlin.math.abs

fun CarouselLayoutManager.findCenterView(): View? {
    val childCount = childCount
    val parentCenter = width / 2

    var closestChild: View? = null
    var minDistance = Int.MAX_VALUE

    for (i in 0 until childCount) {
        val child = getChildAt(i) ?: continue
        val childCenter = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2
        val distance = abs(childCenter - parentCenter)

        if (distance < minDistance) {
            minDistance = distance
            closestChild = child
        }
    }

    return closestChild
}
