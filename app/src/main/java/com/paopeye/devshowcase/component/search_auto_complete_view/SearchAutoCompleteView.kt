package com.paopeye.devshowcase.component.search_auto_complete_view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.databinding.SearchAutoCompleteViewBinding
import com.paopeye.domain.model.CityAutoComplete
import com.paopeye.kit.extension.emptyString

class SearchAutoCompleteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnFocusChangeListener {
    private var binding: SearchAutoCompleteViewBinding
    private val searchAutoCompleteAdapter = SearchAutoCompleteAdapter()
    private var onClickAutoComplete: (CityAutoComplete) -> Unit? = {}
    private var onFocusChangeListener: OnFocusChangeListener? = null
    private var clearButtonDrawable: Drawable? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = SearchAutoCompleteViewBinding.inflate(inflater, this, true)
        clearButtonDrawable = ContextCompat.getDrawable(context, R.drawable.ic_clear_24dp)
        setupView()
        setupAutoComplete()
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        onFocusChangeListener?.onFocusChange(view, hasFocus)
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return binding.searchBarView.requestFocus(direction, previouslyFocusedRect)
    }

    override fun clearFocus() {
        super.clearFocus()
        binding.searchBarView.clearFocus()
        hideKeyboard()
    }

    private fun setupView() {
        isFocusable = true
        isFocusableInTouchMode = true
        super.setOnFocusChangeListener { _, hasFocus ->
            onFocusChangeListener?.onFocusChange(this, hasFocus)
        }
        binding.cancelText.setOnClickListener {
            clearFocus()
            binding.searchBarView.query = emptyString()
        }
        binding.searchBarView.setOnFocusChangeListener { _, hasFocus ->
            binding.cancelText.isVisible = hasFocus
            onFocusChangeListener?.onFocusChange(this, hasFocus)
        }
        binding.searchBarView.setOnTextChangedListener {
            setVisibilityPopup(it.isNotEmpty())
        }
        binding.searchBarView.setOnClearIconClickListener {
            searchAutoCompleteAdapter.cities = emptyList()
        }
    }

    private fun setupAutoComplete() {
        binding.autoCompleteRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAutoCompleteAdapter
        }
        searchAutoCompleteAdapter.setOnClickListener {
            onClickAutoComplete.invoke(it)
            setVisibilityPopup(false)
            hideKeyboard()
        }
    }

    private fun setVisibilityPopup(isVisible: Boolean) {
        binding.autoCompleteRecycler.isVisible = isVisible
    }

    private fun hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun setOnTextChangedListener(callback: (String) -> Unit) {
        binding.searchBarView.setOnDebounceTextChangedListener(callback)
    }

    fun setOnClickAutoComplete(callback: (CityAutoComplete) -> Unit) {
        onClickAutoComplete = callback
    }

    fun setAutoCompleteData(suggestions: List<CityAutoComplete>) {
        searchAutoCompleteAdapter.cities = suggestions
    }

    fun setLoading(loading: Boolean) {
        binding.searchBarView.setLoading(loading)
    }

    fun detachTextWatcher() {
        binding.searchBarView.detachTextWatcher()
    }

}
