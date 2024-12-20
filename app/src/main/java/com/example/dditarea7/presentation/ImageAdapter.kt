package com.example.dditarea7.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dditarea7.R

class ImageAdapter(
    private val imageResIds: List<Int>
) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.image_layout, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: ImageViewHolder,
        position: Int
    ) {

        val imageResId = imageResIds[position]
        viewHolder.imageView.setImageResource(imageResId)
    }

    override fun getItemCount() = imageResIds.size
}