package com.example.dditarea7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dditarea7.presentation.ImageAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_images)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val imageResIds = listOf(
            R.drawable.z_01,
            R.drawable.z_02,
            R.drawable.z_03,
            R.drawable.z_04,
            R.drawable.z_05,
            R.drawable.z_06
        )

        val adapter = ImageAdapter(imageResIds)
        recyclerView.adapter = adapter
    }
}