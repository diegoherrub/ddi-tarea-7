package com.example.dditarea7.app

import android.view.View
import com.google.android.material.carousel.CarouselLayoutManager
import kotlin.math.abs

/**
 * Extension function for the CarouselLayoutManager to find the view that is closest to the center of the layout.
 * This is useful for determining which item in the carousel is currently centered on the screen.
 *
 * @return The View that is closest to the center of the CarouselLayoutManager, or null if no views are present.
 */
fun CarouselLayoutManager.findCenterView(): View? {
    val childCount = childCount // Get the total number of child views in the layout.
    val parentCenter = width / 2 // Calculate the center point of the parent layout.

    var closestChild: View? = null // Variable to store the closest view to the center.
    var minDistance = Int.MAX_VALUE // Variable to store the minimum distance found.

    // Iterate through all child views in the layout.
    for (i in 0 until childCount) {
        val child = getChildAt(i) ?: continue // Get the child at index i, skipping if it's null.
        val childCenter = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2 // Calculate the center of the child view.
        val distance = abs(childCenter - parentCenter) // Calculate the absolute distance from the parent's center.

        // Update the closest child and minimum distance if the current distance is smaller.
        if (distance < minDistance) {
            minDistance = distance
            closestChild = child
        }
    }

    // Return the closest child view to the center, or null if no views are present.
    return closestChild
}
