package com.example.dditarea7.presentation

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dditarea7.R

/**
 * ViewHolder class for binding and displaying the image in the RecyclerView item.
 *
 * This class holds the reference to the `ImageView` in the layout and is responsible for
 * binding the data provided by the adapter to the view.
 *
 * @param itemView The view representing a single item in the RecyclerView.
 */
class ImageViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    /**
     * The ImageView used to display the image for the RecyclerView item.
     * It is initialized by finding the view with the ID `R.id.image_view` in the layout.
     */
    val imageView: ImageView = itemView.findViewById(R.id.image_view)
}
