package com.paopeye.devshowcase.component.bottom_sheet_web_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.paopeye.devshowcase.component.web_view.CustomWebView
import com.paopeye.devshowcase.databinding.BottomSheetWebViewBinding
import android.R as RAndroid
import com.google.android.material.R as RMaterial

class BottomSheetWebView(context: Context) : FrameLayout(context) {
    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)
    private val binding: BottomSheetWebViewBinding
    private var onStateHiddenListener: () -> Unit = {}

    init {
        val inflater = LayoutInflater.from(context)
        binding = BottomSheetWebViewBinding.inflate(inflater, this, true)
        inflateLayout()
        setupBottomSheetBehaviour()
        setupWebViewBehaviour()
        bottomSheetDialog.setOnCancelListener {
            onStateHiddenListener.invoke()
        }
    }

    private fun inflateLayout() {
        bottomSheetDialog.setContentView(this)
        bottomSheetDialog.window?.findViewById<View>(RMaterial.id.design_bottom_sheet)
            ?.setBackgroundResource(RAndroid.color.transparent);
    }

    private fun setupBottomSheetBehaviour() {
        (parent as? View)?.let { view ->
            BottomSheetBehavior.from(view).let { behaviour ->
                behaviour.addBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_DRAGGING && binding.webView.scrollY > 0) {
                            behaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                        } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            onStateHiddenListener.invoke()
                            close()
                        }
                    }
                })
            }
        }
    }

    private fun setupWebViewBehaviour() {
        binding.webView.onLoadingStateChanged = object : CustomWebView.OnLoadingStateChangedListener {
            override fun onLoadingStateChanged(isLoading: Boolean) {
                binding.loadingView.isVisible = isLoading
            }
        }
    }

    fun showWithUrl(url: String) {
        binding.webView.loadUrl(url)
        bottomSheetDialog.show()
    }

    fun close() {
        bottomSheetDialog.dismiss()
    }

    fun setOnStateHidden(callback: () -> Unit) {
        onStateHiddenListener = callback
    }
}
