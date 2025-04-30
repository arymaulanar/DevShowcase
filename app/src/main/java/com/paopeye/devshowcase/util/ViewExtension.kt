package com.paopeye.devshowcase.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.paopeye.devshowcase.R

fun ImageView.loadImageWithUrl(
    context: Context,
    url: String?
) {
    val errorImage = Glide.with(context.applicationContext)
        .load(R.drawable.ic_alert_triangle_24dp)
    Glide.with(context.applicationContext)
        .load(url)
        .placeholder(R.drawable.ic_photo_24dp)
        .error(errorImage)
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
