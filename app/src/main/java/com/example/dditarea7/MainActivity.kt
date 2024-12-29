package com.example.dditarea7

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.MenuInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.dditarea7.data.local.LocalMockImages
import com.example.dditarea7.app.findCenterView
import com.example.dditarea7.presentation.ImageAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.FullScreenCarouselStrategy

/**
 * Main activity that serves as the entry point of the application.
 *
 * This activity handles the initialization and interaction of various UI components,
 * including a toolbar, a carousel with indicators, buttons, and expandable content.
 */
class MainActivity : AppCompatActivity() {

    // Flag to manage the description expansion state
    private var isDescriptionExpanded = false

    /**
     * Called when the activity is starting. This method initializes the UI components
     * and sets up event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     * being shut down, this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure "More" menu button
        val buttonMore = findViewById<ImageButton>(R.id.button_more)
        buttonMore.setOnClickListener { view -> setupPopupMenu(view) }

        // Configure "Back" button
        val buttonBack = findViewById<ImageButton>(R.id.button_back)
        buttonBack.setOnClickListener {
            Toast.makeText(this, "Back clicked", Toast.LENGTH_SHORT).show()
        }

        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val scrollView = findViewById<ScrollView>(R.id.main)

        // Configure toolbar color and icon behavior based on scroll position
        configureToolbar(scrollView, toolbar, buttonBack, buttonMore, toolbarTitle)

        // Initialize mock images
        val localMockImages = LocalMockImages().get()

        // Set up the RecyclerView with carousel
        val carouselRecyclerView: RecyclerView = findViewById(R.id.carousel_recycler_view)
        val adapter = ImageAdapter(localMockImages)
        carouselRecyclerView.adapter = adapter
        carouselRecyclerView.layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy())

        setupIndicators(localMockImages.size)
        updateIndicators(0)

        // Listen for carousel scroll changes
        carouselRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as CarouselLayoutManager
                val centerView = layoutManager.findCenterView()
                val position = centerView?.let { layoutManager.getPosition(it) } ?: return
                updateIndicators(position)
            }
        })

        // Configure the "Like" button
        setupLikeButton()

        // Configure the "Send" button
        val sendButton: MaterialButton = findViewById(R.id.button_send)
        sendButton.setOnClickListener {
            Toast.makeText(this, "Send clicked", Toast.LENGTH_SHORT).show()
        }

        // Configure expandable description content
        setupExpandableDescription()

        // Configure the "Make Offer" button
        val makeOfferButton: MaterialButton = findViewById(R.id.button_make_an_offer)
        makeOfferButton.setOnClickListener {
            Toast.makeText(this, "Make offer clicked", Toast.LENGTH_SHORT).show()
        }

        // Configure the "Buy" button
        val buyButton: MaterialButton = findViewById(R.id.button_buy)
        buyButton.setOnClickListener {
            Toast.makeText(this, "Buy clicked", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Configures the popup menu for the "More" button.
     *
     * @param view The view to anchor the popup menu.
     */
    private fun setupPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_more_options, popupMenu.menu)

        // Apply custom style to the "Report" menu item
        val menuItemReport = popupMenu.menu.findItem(R.id.action_report)
        val spannableTitle = SpannableString(menuItemReport.title).apply {
            setSpan(
                ForegroundColorSpan(getColor(R.color.md_theme_error)),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        menuItemReport.title = spannableTitle

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_report -> {
                    handleReportAction()
                    true
                }
                R.id.action_share -> {
                    handleShareAction()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    /**
     * Handles the "Report" action.
     */
    private fun handleReportAction() {
        Toast.makeText(this, "Option Report clicked", Toast.LENGTH_SHORT).show()
    }

    /**
     * Handles the "Share" action.
     */
    private fun handleShareAction() {
        Toast.makeText(this, "Option Share clicked", Toast.LENGTH_SHORT).show()
    }

    /**
     * Configures the behavior of the toolbar during scrolling.
     */
    private fun configureToolbar(
        scrollView: ScrollView,
        toolbar: Toolbar,
        buttonBack: ImageButton,
        buttonMore: ImageButton,
        toolbarTitle: TextView
    ) {
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val maxScroll = 450
            val alpha = if (scrollY < maxScroll) scrollY.toFloat() / maxScroll else 1f

            toolbar.setBackgroundColor(adjustAlpha(getColor(R.color.md_theme_primaryContainer), alpha))
            val iconColor = interpolateColor(
                getColor(R.color.icon_initial_color_buttons_toolbar),
                getColor(R.color.icon_final_color_buttons_toolbar),
                alpha
            )
            val titleColor = interpolateColor(
                getColor(R.color.title_initial_color_toolbar),
                getColor(R.color.title_final_color_toolbar),
                alpha
            )
            buttonBack.setColorFilter(iconColor)
            buttonMore.setColorFilter(iconColor)
            toolbarTitle.setTextColor(titleColor)
        }
    }

    /**
     * Sets up the "Like" button functionality.
     */
    private fun setupLikeButton() {
        val likeButton: MaterialButton = findViewById(R.id.button_like)
        var isLiked = false
        var likeCount = likeButton.text.toString().toIntOrNull() ?: 0

        likeButton.setIconResource(R.drawable.favorite_line)
        likeButton.iconTint = getColorStateList(R.color.indicator_orange)
        likeButton.setOnClickListener {
            isLiked = !isLiked
            likeCount += if (isLiked) 1 else -1
            likeButton.setIconResource(if (isLiked) R.drawable.favorite_filled else R.drawable.favorite_line)
            likeButton.iconTint = getColorStateList(R.color.indicator_orange)
            likeButton.text = likeCount.toString()
        }
    }

    /**
     * Sets up the expandable description functionality.
     */
    private fun setupExpandableDescription() {
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
    }

    /**
     * Initializes the indicators for the carousel.
     *
     * @param count The total number of indicators to create.
     */
    private fun setupIndicators(count: Int) {
        val indicatorContainer = findViewById<LinearLayout>(R.id.carousel_indicator)
        indicatorContainer.removeAllViews()
        for (i in 0 until count) {
            val indicator = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(20, 20).apply {
                    marginEnd = 16
                }
                setBackgroundResource(R.drawable.indicator_unselected)
            }
            indicatorContainer.addView(indicator)
        }
    }

    /**
     * Updates the indicators based on the selected position in the carousel.
     *
     * @param selectedPosition The currently selected position.
     */
    private fun updateIndicators(selectedPosition: Int) {
        val indicatorContainer = findViewById<LinearLayout>(R.id.carousel_indicator)
        for (i in 0 until indicatorContainer.childCount) {
            val indicator = indicatorContainer.getChildAt(i)
            val isActive = i == selectedPosition
            indicator.setBackgroundResource(
                if (isActive) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )
        }
    }

    /**
     * Adjusts the alpha value of a color.
     *
     * @param color The original color.
     * @param factor The alpha factor (0 to 1).
     * @return The adjusted color.
     */
    private fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = (255 * factor).toInt()
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))
    }

    /**
     * Interpolates between two colors based on a factor.
     *
     * @param startColor The starting color.
     * @param endColor The ending color.
     * @param factor The interpolation factor (0 to 1).
     * @return The interpolated color.
     */
    private fun interpolateColor(startColor: Int, endColor: Int, factor: Float): Int {
        return Color.argb(
            (Color.alpha(startColor) + factor * (Color.alpha(endColor) - Color.alpha(startColor))).toInt(),
            (Color.red(startColor) + factor * (Color.red(endColor) - Color.red(startColor))).toInt(),
            (Color.green(startColor) + factor * (Color.green(endColor) - Color.green(startColor))).toInt(),
            (Color.blue(startColor) + factor * (Color.blue(endColor) - Color.blue(startColor))).toInt()
        )
    }
}
