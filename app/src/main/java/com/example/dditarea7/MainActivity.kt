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

class MainActivity : AppCompatActivity() {

    // Toolbar components
    private lateinit var toolBar: Toolbar
    private lateinit var toolBarButtonBack: ImageButton
    private lateinit var toolBarTitle: TextView
    private lateinit var toolBarButtonMore: ImageButton

    // Scroll View
    private lateinit var scrollView: ScrollView

    // Carousel components
    private lateinit var localMockImages: List<Int>
    private lateinit var carouselRecyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
    private lateinit var sendButton: MaterialButton

    // Flag to manage the description expansion state
    private var isDescriptionExpanded = false

    // Bottom action buttons
    private lateinit var makeOfferButton: MaterialButton
    private lateinit var buyButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolBar()
        scrollView = findViewById(R.id.main)
        setupCarousel()

        configureToolbar(scrollView, toolBar, toolBarButtonBack, toolBarButtonMore, toolBarTitle)

        setupLikeButton()
        setupExpandableDescription()
        setUpBottomButtons()
    }

    /**
     * Sets up the toolbar and its components (Back and More buttons).
     */
    private fun setUpToolBar() {
        toolBar = findViewById(R.id.toolbar)
        toolBarButtonBack = findViewById(R.id.button_back)
        toolBarTitle = findViewById(R.id.toolbar_title)
        toolBarButtonMore = findViewById(R.id.button_more)

        toolBarButtonBack.setOnClickListener {
            Toast.makeText(this, "Back clicked", Toast.LENGTH_SHORT).show()
        }
        toolBarButtonMore.setOnClickListener { view ->
            setupPopupMenu(view)
        }
    }

    /**
     * Configures the behavior of the toolbar during scrolling.
     *
     * @param scrollView The main scrollable container.
     * @param toolbar The toolbar whose appearance is modified.
     * @param buttonBack The back button in the toolbar.
     * @param buttonMore The more options button in the toolbar.
     * @param toolbarTitle The title displayed in the toolbar.
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

            // Adjust toolbar background and icon colors
            toolbar.setBackgroundColor(
                adjustAlpha(
                    getColor(R.color.md_theme_primaryContainer),
                    alpha
                )
            )
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
     * Sets up the popup menu for the "More" button.
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
                    Toast.makeText(this, "Option Report clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.action_share -> {
                    Toast.makeText(this, "Option Share clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
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

    /**
     * Sets up the carousel RecyclerView with images and indicators.
     */
    private fun setupCarousel() {
        localMockImages = LocalMockImages().get()
        carouselRecyclerView = findViewById(R.id.carousel_recycler_view)
        adapter = ImageAdapter(localMockImages)
        sendButton = findViewById(R.id.button_send)

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

        sendButton.setOnClickListener {
            Toast.makeText(this, "Send clicked", Toast.LENGTH_SHORT).show()
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
     * Sets up the bottom action buttons ("Make Offer" and "Buy").
     */
    private fun setUpBottomButtons() {
        makeOfferButton = findViewById(R.id.button_make_an_offer)
        buyButton = findViewById(R.id.button_buy)

        makeOfferButton.setOnClickListener {
            Toast.makeText(this, "Make offer clicked", Toast.LENGTH_SHORT).show()
        }
        buyButton.setOnClickListener {
            Toast.makeText(this, "Buy clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
