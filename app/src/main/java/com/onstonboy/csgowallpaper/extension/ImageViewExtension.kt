package com.onstonboy.csgowallpaper.extension

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.onstonboy.csgowallpaper.R

fun ImageView.loadUrl(url: String?) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
        .into(this)
}

fun ImageView.loadResource(drawable: Int) {
    Glide.with(context)
        .load(drawable)
        .into(this)
}

fun ImageView.loadUri(uri: Uri) {
    Glide.with(context)
        .load(uri)
        .into(this)
}

fun ImageView.loadUrlWithPlaceHolder(url: String?, drawableRes: Int) {
    if (url.isNullOrBlank()) {
        loadResource(drawableRes)
    } else {
        val drawable = AppCompatResources.getDrawable(context, drawableRes)
        Glide.with(context)
            .load(url)
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .apply(
                RequestOptions().placeholder(drawable)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(this)
    }
}

fun ImageView.loadUrlWithRoundCorner(uri: Uri?, radius: Int? = null) {
    val defaultRadius = radius ?: 8
    Glide.with(context)
        .load(uri)
        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(defaultRadius)))
        .into(this)
}

fun ImageView.loadUrlWithError(url: String?, drawable: Int) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().error(drawable).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
        .into(this)
}

fun ImageView.loadUrlWithoutCache(url: String, @DrawableRes drawableRes: Int) {
    val drawable = AppCompatResources.getDrawable(context, drawableRes)
    Glide.with(this)
        .load(url)
        .transition(GenericTransitionOptions.with(R.anim.fade_in))
        .apply(
            RequestOptions().placeholder(drawable)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
        )
        .into(this)
}
