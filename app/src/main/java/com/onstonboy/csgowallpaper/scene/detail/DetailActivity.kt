package com.onstonboy.csgowallpaper.scene.detail

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import com.onstonboy.csgowallpaper.BaseActivity
import com.onstonboy.csgowallpaper.R
import com.onstonboy.csgowallpaper.extension.*
import com.onstonboy.csgowallpaper.model.Wallpaper
import com.onstonboy.csgowallpaper.utils.*
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.IOException

class DetailActivity : BaseActivity(), SliderAdapter.OnItemClickListener {

    private lateinit var mPermission: PermissionHandler
    private lateinit var mSliderAdapter: SliderAdapter
    private lateinit var mDialogManager: DialogManager

    private var mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mPermission = PermissionHandlerImpl(this)
        mDialogManager = DialogManagerImpl(this)

        initData()
    }

    override fun onDestroy() {
        mSliderAdapter.setOnItemClickListener(null)
        mCompositeDisposable.clear()
        super.onDestroy()
    }

    override fun onItemClick(wallpaper: Wallpaper) {
        changeWhiteUI()
    }

    fun onDownloadClick(view: View) {
        if (mPermission.isPermissionStorageGranted()) {
            downloadImage()
        }
    }

    fun onSetWallpaperClick(view: View) {
        if (!mPermission.isPermissionSetWallpaperGranted()) return
        mDialogManager.showProgressDialog(getString(R.string.processing))
        val disposable = Single.create<Bitmap> { emitter ->
            val bitmap =
                FileUtils.getBitmapFromURL(mSliderAdapter.dataList[discreteScrollView.currentItem].url)
            if (bitmap == null) {
                emitter.onError(Throwable("Null"))
            } else {
                emitter.onSuccess(bitmap)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setImageWallpaper(it)
            }, {
                logError(TAG, "error", Exception(it))
            })
        mCompositeDisposable.add(disposable)
    }

    private fun setImageWallpaper(bitmap: Bitmap) {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        val bitmapScaled = Bitmap.createScaledBitmap(bitmap, width, height, true)
        val wallpaperManager = WallpaperManager.getInstance(this)
        try {
            wallpaperManager.setBitmap(bitmapScaled)
            mDialogManager.dismissProgressDialog()
            Toast.makeText(this, getString(R.string.set_wallpaper_successfully), Toast.LENGTH_SHORT)
                .show()
        } catch (e: IOException) {
            logError(TAG, "error", e)
        }
    }

    private fun downloadImage() {
        val currentWallpaper = mSliderAdapter.dataList[discreteScrollView.currentItem]
        val request = DownloadManager.Request(Uri.parse(currentWallpaper.url))
        request.setTitle(currentWallpaper.name)
            .setMimeType("image/jpg")
            .setDescription(getString(R.string.download))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                currentWallpaper.name
            )

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager
        downloadManager.notNull { it.enqueue(request) }
        Toast.makeText(this, getString(R.string.downloading), Toast.LENGTH_SHORT)
            .show()
    }

    private fun changeWhiteUI() {
        if (!bottomMenuView.isVisible()) {
            backgroundView.showFadeIn()
            bottomMenuView.slideUp(300)
        } else {
            backgroundView.goneFadeOut()
            bottomMenuView.slideDown(300)
        }
    }

    private fun initData() {
        val wallpapers = intent.getParcelableArrayListExtra<Wallpaper>(Constant.Extra.WALLPAPERS)
        val indexSelected = intent.getIntExtra(Constant.Extra.INDEX_SELECTED, 0)

        mSliderAdapter = SliderAdapter(this)
        discreteScrollView.adapter = mSliderAdapter

        discreteScrollView.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )

        mSliderAdapter.updateData(wallpapers)
        discreteScrollView.scrollToPosition(indexSelected)
        mSliderAdapter.setOnItemClickListener(this)
    }

    companion object {
        private var TAG = this::class.java.simpleName

        fun getInstance(context: Context, wallpapers: List<Wallpaper>, indexSelected: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putParcelableArrayListExtra(Constant.Extra.WALLPAPERS, ArrayList(wallpapers))
            intent.putExtra(Constant.Extra.INDEX_SELECTED, indexSelected)
            return intent
        }
    }
}
