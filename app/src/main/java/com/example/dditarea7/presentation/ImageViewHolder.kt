package com.example.dditarea7.presentation

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dditarea7.R

class ImageViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    val imageView: ImageView = itemView.findViewById(R.id.image_view)
}