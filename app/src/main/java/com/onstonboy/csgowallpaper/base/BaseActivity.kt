package com.onstonboy.csgowallpaper.base

import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

abstract class BaseActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    fun logError(tag: String, message: String, exception: Exception) {
        Log.e(tag, message, exception)
    }
}
