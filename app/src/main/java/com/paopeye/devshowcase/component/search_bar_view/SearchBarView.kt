package com.paopeye.devshowcase.component.search_bar_view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.databinding.SearchBarViewBinding
import com.paopeye.devshowcase.util.DebounceTextWatcher
import com.paopeye.kit.extension.emptyString

class SearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnFocusChangeListener {
    private var binding: SearchBarViewBinding
    private var textWatcher: DebounceTextWatcher? = null
    private var onDebounceTextChangedListener: (String) -> Unit? = {}
    private var onTextChangedListener: (String) -> Unit? = {}
    private var onFocusChangeListener: OnFocusChangeListener? = null
    private var onClearIconClickListener: () -> Unit = {}
    private var clearButtonDrawable: Drawable? = null
    private var isLoading = false

    var query: String = emptyString()
        set(value) {
            field = value
            binding.queryEdit.setText(field)
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = SearchBarViewBinding.inflate(inflater, this, true)
        initializeAttributes()
        setupView()
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return binding.queryEdit.requestFocus(direction, previouslyFocusedRect)
    }

    override fun clearFocus() {
        super.clearFocus()
        binding.queryEdit.clearFocus()
        hideKeyboard()
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        onFocusChangeListener?.onFocusChange(view, hasFocus)
    }

    private fun initializeAttributes() {
        clearButtonDrawable = ContextCompat.getDrawable(context, R.drawable.ic_clear_24dp)
    }

    private fun setupView() {
        textWatcher = DebounceTextWatcher(
            500L,
            onDebouncedTextChanged = { onDebounceTextChangedListener(it) },
            onTextChanged = {
                updateRightImageState()
                onTextChangedListener(it)
            }
        )
        binding.queryEdit.addTextChangedListener(textWatcher)
        isFocusable = true
        isFocusableInTouchMode = true
        super.setOnFocusChangeListener { _, hasFocus ->
            onFocusChangeListener?.onFocusChange(this, hasFocus)
        }
        binding.queryEdit.setOnFocusChangeListener { _, hasFocus ->
            onFocusChangeListener?.onFocusChange(this, hasFocus)
        }
        binding.rightImage.setImageDrawable(clearButtonDrawable)
        binding.rightImage.setOnClickListener {
            binding.queryEdit.setText(emptyString())
            onClearIconClickListener.invoke()
        }
    }

    private fun updateRightImageState() {
        if (isLoading) {
            binding.rightImage.visibility = GONE
            binding.loadingView.visibility = VISIBLE
            return
        }
        if (binding.queryEdit.text.toString().isNotEmpty()) {
            binding.rightImage.visibility = VISIBLE
            binding.loadingView.visibility = GONE
            return
        }
        binding.loadingView.visibility = GONE
        binding.rightImage.visibility = GONE
    }

    private fun hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun setOnTextChangedListener(callback: (String) -> Unit) {
        onTextChangedListener = callback
    }

    fun setOnDebounceTextChangedListener(callback: (String) -> Unit) {
        onDebounceTextChangedListener = callback
    }

    fun setOnClearIconClickListener(callback: () -> Unit) {
        onClearIconClickListener = callback
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
        updateRightImageState()
    }

    fun detachTextWatcher() {
        textWatcher?.detach()
    }
}
