package com.paopeye.devshowcase.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.paopeye.devshowcase.R
import com.paopeye.kit.extension.emptyFloat
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.silence

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
        .centerCrop()
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

    loadImageWithFallbackRecursive(
        Glide.with(context.applicationContext),
        urls,
        0,
        placeholder,
        errorDrawable
    )
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

fun View.setFullScreenMarginTop() = silence {
    setOnApplyWindowInsetsListener { _, insets ->
        val param = layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = insets.getSupportSystemWindowInsetTop()
        layoutParams = layoutParams as ViewGroup.MarginLayoutParams
        insets
    }
}

fun WindowInsets.getSupportSystemWindowInsetTop(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        return getInsets(WindowInsets.Type.systemBars()).top
    }
    @Suppress("DEPRECATION")
    return systemWindowInsetTop
}

fun applyGradientToTitle(titleView: TextView, imageView: ImageView) {
    val gradient = LinearGradient(
        emptyFloat(), emptyFloat(), emptyFloat(), titleView.height.toFloat(),
        intArrayOf(Color.TRANSPARENT, Color.BLACK),
        floatArrayOf(emptyFloat(), 1f),
        Shader.TileMode.CLAMP
    )

    titleView.paint.shader = gradient
    titleView.setTextColor(Color.WHITE)
    imageView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            adjustTitleForImage(titleView, imageView)
        }
    })
}

private fun adjustTitleForImage(titleView: TextView, imageView: ImageView) {
    val bitmap = (imageView.drawable as? BitmapDrawable)?.bitmap
    if (bitmap != null) {
        val bottomColor = getDominantColorFromArea(bitmap,
            Rect(emptyInt(), bitmap.height - 20, bitmap.width, bitmap.height)
        )

        val endColor = if (isColorDark(bottomColor)) {
            Color.BLACK
        } else {
            Color.argb(200, emptyInt(), emptyInt(), emptyInt())
        }

        val gradient = LinearGradient(
            emptyFloat(), emptyFloat(), emptyFloat(), titleView.height.toFloat(),
            intArrayOf(Color.TRANSPARENT, endColor),
            floatArrayOf(emptyFloat(), 1f),
            Shader.TileMode.CLAMP
        )

        titleView.paint.shader = gradient
    }
}

private fun getDominantColorFromArea(bitmap: Bitmap, area: Rect): Int {
    val cropped = Bitmap.createBitmap(bitmap, area.left, area.top,
        area.width(), area.height())
    return Palette.from(cropped).generate().getDominantColor(Color.BLACK)
}

private fun isColorDark(color: Int): Boolean {
    val darknessThreshold = 0.5
    val red = Color.red(color) / 255.0
    val green = Color.green(color) / 255.0
    val blue = Color.blue(color) / 255.0
    val luminance = 0.2126 * red + 0.7152 * green + 0.0722 * blue
    return luminance < darknessThreshold
}
