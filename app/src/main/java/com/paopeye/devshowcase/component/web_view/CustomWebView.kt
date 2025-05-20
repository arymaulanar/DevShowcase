package com.paopeye.devshowcase.component.web_view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebView : WebView {
    var onScrollChangedCallback: OnScrollChangeListener? = null
    var onLoadingStateChanged: OnLoadingStateChangedListener? = null

    constructor(context: Context) : super(context) {
        initWebViewClient()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initWebViewClient()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initWebViewClient()
    }

    private fun initWebViewClient() {
        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                onLoadingStateChanged?.onLoadingStateChanged(true)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                onLoadingStateChanged?.onLoadingStateChanged(false)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                onLoadingStateChanged?.onLoadingStateChanged(false)
            }
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        onScrollChangedCallback?.onScrollChanged(l, t, oldl, oldt)
    }

    interface OnScrollChangeListener {
        fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)
    }

    interface OnLoadingStateChangedListener {
        fun onLoadingStateChanged(isLoading: Boolean)
    }
}
