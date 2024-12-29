package com.example.dditarea7

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import com.example.dditarea7.data.local.LocalMockImages
import com.example.dditarea7.app.findCenterView
import com.example.dditarea7.presentation.ImageAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.FullScreenCarouselStrategy

class MainActivity : AppCompatActivity() {

    private var isDescriptionExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurate menu more
        val buttonMore = findViewById<ImageButton>(R.id.button_more)
        buttonMore.setOnClickListener { view ->

            // Create the popup menu
            val popupMenu = PopupMenu(this, view)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.menu_more_options, popupMenu.menu)

            // Apply the custom style to the popup menu item "Report"
            val menuItemReport = popupMenu.menu.findItem(R.id.action_report)
            val spannableTitle = SpannableString(menuItemReport.title)
            spannableTitle.setSpan(
                ForegroundColorSpan(getColor(R.color.md_theme_error)),
                0,
                spannableTitle.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            menuItemReport.title = spannableTitle

            // Handle the click selected option
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_report -> {
                        // Handle settings option click
                        handleReportAction()
                        true
                    }

                    R.id.action_share -> {
                        // Handle share option click
                        handleShareAction()
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }

        // Configurate the toolbar for change display
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val scrollView = findViewById<ScrollView>(R.id.main)
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // Altura máxima para que la Toolbar sea totalmente opaca
            val maxScroll = 450 // Ajusta este valor según el diseño
            // Calcula el porcentaje de desplazamiento
            val alpha = if (scrollY < maxScroll) {
                scrollY.toFloat() / maxScroll
            } else {
                1f
            }
            // Actualiza el color de fondo de la Toolbar con el alpha calculado
            toolbar.setBackgroundColor(adjustAlpha(getColor(R.color.md_theme_primary), alpha))
        }

        // Instancio las imágenes
        var localMockImages = LocalMockImages().get()

        // Configuro el RecyclerView
        val carouselRecyclerView: RecyclerView = findViewById(R.id.carousel_recycler_view)
        val adapter = ImageAdapter(localMockImages)
        carouselRecyclerView.adapter = adapter
        carouselRecyclerView.layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy())

        setupIndicators(localMockImages.size)
        updateIndicators(0)

        carouselRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as CarouselLayoutManager
                val centerView = layoutManager.findCenterView()
                val position = centerView?.let { layoutManager.getPosition(it) } ?: return

                updateIndicators(position)
            }
        })

        // Set up like button
        val likeButton: MaterialButton = findViewById(R.id.button_like)
        var isLiked = false
        var likeCount = likeButton.text.toString().toIntOrNull() ?: 0

        likeButton.setIconResource(R.drawable.favorite_line)
        likeButton.iconTint = getColorStateList(R.color.indicator_orange)
        likeButton.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                likeCount++
                likeButton.setIconResource(R.drawable.favorite_filled)
                likeButton.iconTint = getColorStateList(R.color.indicator_orange)
            } else {
                likeCount--
                likeButton.setIconResource(R.drawable.favorite_line)
                likeButton.iconTint = getColorStateList(R.color.indicator_orange)
            }

            likeButton.text = likeCount.toString() // Actualiza el texto del botón directamente
        }

        // Send Button Toast
        val sendButton: MaterialButton = findViewById(R.id.button_send)
        sendButton.setOnClickListener{
            Toast.makeText(this, "Send clicked", Toast.LENGTH_SHORT).show()
        }

        // Set up expand description button
        val descriptionContent: TextView = findViewById(R.id.description_content)
        val expandText: TextView = findViewById(R.id.expand_text)
        expandText.setOnClickListener {
            if (isDescriptionExpanded) {
                descriptionContent.maxLines = 2
                descriptionContent.ellipsize = TextUtils.TruncateAt.END
                expandText.text = getString(R.string.string_expand_text)
            } else {
                descriptionContent.maxLines = Int.MAX_VALUE
                descriptionContent.ellipsize = null
                expandText.text = getString(R.string.string_contract_text)
            }
            isDescriptionExpanded = !isDescriptionExpanded
        }

        // Set up make offer button
        val makeOfferButton: MaterialButton = findViewById(R.id.button_make_an_offer)
        makeOfferButton.setOnClickListener {
            Toast.makeText(this, "Make offer clicked", Toast.LENGTH_SHORT).show()
        }

        // Set up buy button
        val buyButton: MaterialButton = findViewById(R.id.button_buy)
        buyButton.setOnClickListener {
            Toast.makeText(this, "Buy clicked", Toast.LENGTH_SHORT).show()
        }
    }

    // functions for the menu more
    private fun handleReportAction() {
        // Handle report action
        Toast.makeText(this, "Option Report clicked", Toast.LENGTH_SHORT).show()
    }

    private fun handleShareAction() {
        // Handle share action
        Toast.makeText(this, "Option Share clicked", Toast.LENGTH_SHORT).show()
    }

    private fun setupIndicators(count: Int) {
        val indicatorContainer = findViewById<LinearLayout>(R.id.carousel_indicator)
        indicatorContainer.removeAllViews()

        for (i in 0 until count) {
            val indicator = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(20, 20).apply {
                    marginEnd = 16
                }
                setBackgroundResource(R.drawable.indicator_unselected) // Fondo predeterminado
            }
            indicatorContainer.addView(indicator)
        }
    }

    private fun updateIndicators(selectedPosition: Int) {
        val indicatorContainer = findViewById<LinearLayout>(R.id.carousel_indicator)
        for (i in 0 until indicatorContainer.childCount) {
            val indicator = indicatorContainer.getChildAt(i)
            val isActive = i == selectedPosition
            val backgroundRes = if (isActive) {
                R.drawable.indicator_selected
            } else {
                R.drawable.indicator_unselected
            }
            indicator.setBackgroundResource(backgroundRes)
        }
    }

    private fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = (255 * factor).toInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}