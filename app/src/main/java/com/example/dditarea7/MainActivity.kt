package com.example.dditarea7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dditarea7.presentation.ImageAdapter
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.FullScreenCarouselStrategy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val carouselRecyclerView: RecyclerView = findViewById(R.id.carousel_recycler_view)
        carouselRecyclerView.setLayoutManager(CarouselLayoutManager(FullScreenCarouselStrategy()))

        val listOfImages = listOf(
            R.drawable.z_01,
            R.drawable.z_02,
            R.drawable.z_03,
            R.drawable.z_04,
            R.drawable.z_05,
            R.drawable.z_06
        )

        val adapter = ImageAdapter(listOfImages)
        carouselRecyclerView.adapter = adapter
    }
}