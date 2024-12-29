package com.example.dditarea7.data.local

import com.example.dditarea7.R

/**
 * A class that provides a mock list of drawable resource IDs.
 */
class LocalMockImages {

    /**
     * Returns a list of drawable resource IDs representing mock images.
     *
     * @return A list of integers, where each integer corresponds to a drawable resource ID.
     */
    fun get(): List<Int> {
        return listOf(
            R.drawable.z_01,
            R.drawable.z_02,
            R.drawable.z_03,
            R.drawable.z_04,
            R.drawable.z_05,
            R.drawable.z_06
        )
    }
}
