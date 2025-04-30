package com.paopeye.devshowcase.component.bottom_sheet_web_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.paopeye.devshowcase.databinding.BottomSheetWebViewBinding
import android.R as RAndroid
import com.google.android.material.R as RMaterial

class BottomSheetWebView(context: Context) : FrameLayout(context) {
    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)
    private val binding: BottomSheetWebViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = BottomSheetWebViewBinding.inflate(inflater, this, true)
        inflateLayout()
        setupBottomSheetBehaviour()
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
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    }

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_DRAGGING && binding.webView.scrollY > 0) {
                            behaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                        } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            close()
                        }
                    }
                })
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
}
