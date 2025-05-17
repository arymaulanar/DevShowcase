package com.paopeye.devshowcase.component.custom_toolbar_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.databinding.ToolbarViewBinding
import com.paopeye.kit.extension.emptyString

class CustomToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: ToolbarViewBinding

    var title: String = emptyString()
        set(value) {
            binding.titleText.text = value
            binding.titleText.isVisible = value.isNotBlank()
            field = value
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = ToolbarViewBinding.inflate(inflater, this, true)
    }

    fun leftImageVisibility(isVisible: Boolean) {
        binding.leftImage.isVisible = isVisible
    }

    fun setResourceLeftImage(@DrawableRes resource: Int = R.drawable.ic_back_arrow_24dp) {
        binding.leftImage.setImageResource(resource)
    }

    fun setClickListenerLeftImage(
        onClickListener: () -> Unit
    ) {
        binding.leftImage.setOnClickListener {
            onClickListener()
        }
    }

    fun changeBackgroundColorFromResource(colorResource: Int) {
        binding.toolbar.backgroundTintList = ContextCompat.getColorStateList(context, colorResource)
    }

    fun changeBackgroundColor(color: Int) {
        binding.toolbar.setBackgroundColor(color)
    }
}
