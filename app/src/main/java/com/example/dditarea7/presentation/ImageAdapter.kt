package com.example.dditarea7.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dditarea7.R

/**
 * Adapter class for displaying a list of images in the RecyclerView.
 *
 * This adapter binds a list of drawable resource IDs to the RecyclerView, inflating a specified layout
 * for each image and setting its corresponding resource.
 *
 * @param imageResIds The list of drawable resource IDs to be displayed in the RecyclerView.
 */
class ImageAdapter(
    private val imageResIds: List<Int>
) : RecyclerView.Adapter<ImageViewHolder>() {

    /**
     * Creates a new ViewHolder by inflating the layout for each item in the RecyclerView.
     *
     * @param parent The parent ViewGroup into which the new view will be added after it is bound to
     * an adapter position.
     * @param viewType The type of the new view.
     * @return A new instance of ImageViewHolder for the inflated layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        // Inflate the layout for the individual item.
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.image_layout, parent, false)

        return ImageViewHolder(view)
    }

    /**
     * Binds data to the ViewHolder for the given position in the RecyclerView.
     *
     * @param viewHolder The ViewHolder to which the data should be bound.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(
        viewHolder: ImageViewHolder,
        position: Int
    ) {
        // Get the drawable resource ID for the current position.
        val imageResId = imageResIds[position]
        // Set the drawable resource to the ImageView in the ViewHolder.
        viewHolder.imageView.setImageResource(imageResId)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The size of the image resource ID list.
     */
    override fun getItemCount() = imageResIds.size
}
