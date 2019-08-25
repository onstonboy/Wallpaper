package com.onstonboy.csgowallpaper.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.*
import androidx.annotation.AnimRes
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.startAnimationFromXml(@AnimRes idAnim: Int) {
    val anim = AnimationUtils.loadAnimation(context, idAnim)
    this.startAnimation(anim)
}

fun View.snackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, text, duration).apply { show() }
}

fun View.slideUp(duration: Int = 500) {
    show()
    this.animate().translationY(0f)
        .setListener(null)
}

fun View.slideDown(duration: Int = 500) {
    this.animate().translationY(height.toFloat())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                gone()
            }
        })
}

fun View.showFadeIn(duration: Long = 300) {
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.interpolator = DecelerateInterpolator()
    fadeIn.duration = duration
    val animation = AnimationSet(false)
    animation.addAnimation(fadeIn)
    this.animation = animation
    show()
}

fun View.goneFadeOut(duration: Long = 300) {
    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.interpolator = AccelerateInterpolator()
    fadeOut.startOffset = duration
    fadeOut.duration = duration
    val animation = AnimationSet(false)
    animation.addAnimation(fadeOut)
    this.animation = animation
    gone()
}
