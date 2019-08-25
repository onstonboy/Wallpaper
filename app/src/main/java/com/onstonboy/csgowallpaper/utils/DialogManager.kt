package com.onstonboy.csgowallpaper.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import com.onstonboy.csgowallpaper.R
import com.onstonboy.csgowallpaper.extension.isNull
import com.onstonboy.csgowallpaper.extension.notNull
import kotlinx.android.synthetic.main.layout_progress_bar.view.*

interface DialogManager {
    fun showProgressDialog(message: String)
    fun dismissProgressDialog()
}

class DialogManagerImpl(private var context: Context) : DialogManager {

    private var mProgressDialog: AlertDialog? = null

    override fun showProgressDialog(message: String) {
        mProgressDialog.isNull {
            val view = View.inflate(context, R.layout.layout_progress_bar, null)
            view.messageText.text = message
            mProgressDialog = AlertDialog.Builder(context).setView(view).create()
        }
        mProgressDialog.notNull {
            it.setCanceledOnTouchOutside(false)
            it.show()
        }
    }

    override fun dismissProgressDialog() {
        mProgressDialog.notNull {
            it.dismiss()
            mProgressDialog = null
        }
    }

    companion object {
        private const val TIME_UNIT = 1000
    }
}
