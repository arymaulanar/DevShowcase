package com.paopeye.devshowcase.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.paopeye.devshowcase.R

fun ImageView.loadImageWithUrl(
    context: Context,
    url: String?,
    isHideOnError: Boolean = false
) {
    if (url.isNullOrEmpty() && isHideOnError) {
        visibility = View.GONE
        return
    }
    val errorImage = Glide.with(context)
        .load(R.drawable.ic_alert_triangle_24dp)
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_photo_24dp)
        .error(errorImage)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                if (isHideOnError) {
                    post { visibility = View.GONE }
                }
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                post { visibility = View.VISIBLE }
                return false
            }
        })
        .into(this)
}

fun ImageView.loadImageWithDrawable(
    context: Context,
    url: Drawable,
    @DrawableRes placeholderLayout: Int
) {
    Glide.with(context.applicationContext)
        .load(url)
        .placeholder(placeholderLayout)
        .into(this)
}

fun ImageView.loadWithUrlFallback(
    context: Context,
    urls: List<String>,
    placeholder: Int = R.drawable.ic_photo_24dp,
    errorDrawable: Int = R.drawable.ic_alert_triangle_24dp
) {
    if (urls.isEmpty()) {
        visibility = View.GONE
        return
    }
    visibility = View.VISIBLE

    loadImageWithFallbackRecursive(Glide.with(context.applicationContext), urls, 0, placeholder, errorDrawable)
}

private fun ImageView.loadImageWithFallbackRecursive(
    requestManager: RequestManager,
    urls: List<String>,
    currentIndex: Int,
    placeholder: Int,
    errorDrawable: Int
) {
    if (currentIndex >= urls.size) {
        setImageResource(errorDrawable)
        return
    }

    requestManager
        .load(urls[currentIndex])
        .placeholder(placeholder)
        .error(errorDrawable)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                Handler(Looper.getMainLooper()).post {
                    loadImageWithFallbackRecursive(
                        requestManager,
                        urls,
                        currentIndex + 1,
                        placeholder,
                        errorDrawable
                    )
                }
                return true
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
        .into(this)
}

fun View.dpToPx(dp: Float): Float {
    return dp * resources.displayMetrics.density
}

fun View.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}
