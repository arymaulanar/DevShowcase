package com.paopeye.devshowcase.component.sticky_card_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.paopeye.devshowcase.databinding.StickyCardViewBinding

class StickyCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: StickyCardViewBinding
    private var titleHeight = 0
    private var originalTitlePosition = IntArray(2)
    private var parentView: ViewGroup? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = StickyCardViewBinding.inflate(inflater, this, true)
        binding.titleText.viewTreeObserver.addOnGlobalLayoutListener {
            if (titleHeight == 0) {
                titleHeight = binding.titleText.height
                binding.titleText.getLocationInWindow(originalTitlePosition)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        parentView = parent as? ViewGroup
    }

    fun attachToScrollView(scrollView: NestedScrollView) {
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            updateTitlePosition(scrollY)
        }
    }

    private fun updateTitlePosition(scrollY: Int) {
        if (titleHeight == 0) return

        val screenLocation = IntArray(2)
        getLocationOnScreen(screenLocation)
        val cardTopOnScreen = screenLocation[1] - scrollY

        val parentLocation = IntArray(2)
        parentView?.getLocationOnScreen(parentLocation)
        val relativeTop = screenLocation[1] - parentLocation[1]

        when {
            cardTopOnScreen + height <= 0 -> resetTitlePosition()
            cardTopOnScreen >= resources.displayMetrics.heightPixels -> resetTitlePosition()
            cardTopOnScreen < relativeTop -> {
                val offset = parentLocation[1] - cardTopOnScreen
                binding.titleText.translationY = -offset.toFloat()
                binding.titleText.elevation = 8f
                bringTitleToFront()
            }
            else -> resetTitlePosition()
        }
    }

    private fun resetTitlePosition() {
        binding.titleText.translationY = 0f
        binding.titleText.elevation = 0f
    }

    private fun bringTitleToFront() {
        binding.titleText.z = 1f
    }
}
