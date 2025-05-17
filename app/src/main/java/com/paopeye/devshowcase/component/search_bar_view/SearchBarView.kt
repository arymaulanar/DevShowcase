package com.paopeye.devshowcase.component.search_bar_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.util.DebounceTextWatcher
import com.paopeye.devshowcase.util.dpToPx
import com.paopeye.kit.extension.emptyFloat
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.orEmpty

class SearchBarView : AppCompatEditText, View.OnTouchListener, View.OnFocusChangeListener {
    private var textWatcher: DebounceTextWatcher? = null
    private var clearButtonDrawable: Drawable? = null
    private var searchIconDrawable: Drawable? = null
    private var onFocusChangeListener: OnFocusChangeListener? = null
    private var onTouchListener: OnTouchListener? = null
    private var onTextChangedListener: (String) -> Unit? = {}
    private var autoCompleteAdapter: ArrayAdapter<String>? = null
    private var autoCompletePopup: ListPopupWindow? = null
    private var rootView: ViewGroup? = null
    private var originalElevation: Float = emptyFloat()
    private var aboveSearchBar: Int = emptyInt()
    private var originalBackground: Drawable? = null
    private var isExpanded = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        background = ContextCompat.getDrawable(context, R.drawable.bg_search_edittext)
        compoundDrawablePadding = (resources.displayMetrics.density * 8).toInt()
        clearButtonDrawable = ContextCompat.getDrawable(context, R.drawable.ic_clear_24dp)
        searchIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_search_24dp)
        originalElevation = elevation

        setCompoundDrawablesWithIntrinsicBounds(
            searchIconDrawable,
            null,
            null,
            null
        )
        initAutoComplete()
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        textWatcher = DebounceTextWatcher(
            500L,
            onDebouncedTextChanged = { onTextChangedListener(it) },
            onTextChanged = {
                setClearIconVisible(it.isNotEmpty())
                updateAutoCompleteVisibility(it)
            }
        )
        addTextChangedListener(textWatcher)
    }

    private fun initAutoComplete() {
        autoCompleteAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            mutableListOf()
        )

        autoCompletePopup = ListPopupWindow(context).apply {
            anchorView = this@SearchBarView
            setAdapter(autoCompleteAdapter)
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            setOnItemClickListener { parent, view, position, id ->
                val selectedItem = autoCompleteAdapter?.getItem(position)
                selectedItem?.let {
                    setText(it)
                    setSelection(it.length)
                }
            }
        }
    }

    private fun updateAutoCompleteVisibility(text: CharSequence?) {
        if (text.isNullOrEmpty()) {
            autoCompletePopup?.dismiss()
            dimBackground(false)
        } else {
            autoCompletePopup?.show()
            dimBackground(true)
        }
    }

    private fun dimBackground(shouldDim: Boolean) {
        rootView?.let {
            if (shouldDim) {
                it.background = ColorDrawable(Color.argb(150, 0, 0, 0))
                it.setOnClickListener {
                    clearFocus()
                }
            } else {
                it.background = originalBackground
                it.setOnClickListener(null)
            }
        }
    }

    private fun expand() {
        if (isExpanded) return
        isExpanded = true
        elevation = dpToPx(8F)
        val heightFloat = -aboveSearchBar.toFloat()
        rootView?.animate()?.translationY(heightFloat)?.setDuration(300)?.start()
        dimBackground(true)
    }

    private fun collapse() {
        if (!isExpanded) return
        isExpanded = false
        hideKeyboard()
        elevation = originalElevation
        rootView?.animate()?.translationY(0f)?.setDuration(300)?.start()
        dimBackground(false)
        autoCompletePopup?.dismiss()
    }

    private fun setClearIconVisible(visible: Boolean) {
        val rightDrawable = if (visible) clearButtonDrawable else null
        setCompoundDrawablesWithIntrinsicBounds(
            searchIconDrawable,
            null,
            rightDrawable,
            null
        )
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.onTouchListener = onTouchListener
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus) {
            expand()
            setClearIconVisible(text?.isNotEmpty() == true)
        } else {
            collapse()
            setClearIconVisible(false)
        }
        onFocusChangeListener?.onFocusChange(view, hasFocus)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val x = event.x.toInt()
        if (clearButtonDrawable != null && x > width - paddingRight - clearButtonDrawable?.intrinsicWidth.orEmpty()) {
            if (event.action == MotionEvent.ACTION_UP) {
                text = null
                clearFocus()
                return true
            }
        }
        return onTouchListener?.onTouch(view, event) ?: false
    }

    private fun hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun setSuggestions(suggestions: List<String>) {
        autoCompleteAdapter?.clear()
        autoCompleteAdapter?.addAll(suggestions)
    }

    fun setRootView(root: ViewGroup) {
        this.rootView = root
        originalBackground = root.background
    }

    fun setHeightTopSearchBar(height: Int) {
        aboveSearchBar = height
    }

    fun setOnTextChangedListener(callback: (String) -> Unit) {
        onTextChangedListener = callback
    }

    fun detachTextChangedListener() {
        textWatcher?.detach()
    }
}
