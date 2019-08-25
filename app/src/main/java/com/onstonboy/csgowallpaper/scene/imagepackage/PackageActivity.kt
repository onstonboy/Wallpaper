package com.onstonboy.csgowallpaper.scene.imagepackage

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.onstonboy.csgowallpaper.R
import com.onstonboy.csgowallpaper.base.BaseActivity
import com.onstonboy.csgowallpaper.extension.notNull
import com.onstonboy.csgowallpaper.model.Wallpaper
import com.onstonboy.csgowallpaper.scene.detail.DetailActivity
import com.onstonboy.csgowallpaper.widget.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_image_package.*

class PackageActivity : BaseActivity(), Adapter.OnItemClickListener {

    private lateinit var mAdapter: Adapter
    private lateinit var mLayoutManager: GridLayoutManager

    private var mStorageRef = FirebaseStorage.getInstance().reference
    private var mPageToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_package)

        initData()
        handleGetImages()
        handleEvents()
    }

    override fun onDestroy() {
        mAdapter.setOnItemClickListener(null)
        super.onDestroy()
    }

    override fun onItemClick(wallpaper: Wallpaper) {
        startActivity(
            DetailActivity.getInstance(
                this,
                mAdapter.dataList,
                mAdapter.dataList.indexOf(wallpaper)
            )
        )
    }

    private fun handleGetImages() {
        getImagesFromServer(mPageToken)
    }

    private fun getImagesFromServer(pageToken: String?) {
        val listPageTask = if (pageToken != null) {
            mStorageRef.list(20, pageToken)
        } else {
            mStorageRef.list(20)
        }
        listPageTask
            .addOnSuccessListener { listResult ->
                val items = listResult.items
                items.sortedBy { it.name }.forEach {
                    getUrlFromServer(it.name)
                }
                listResult.pageToken?.let {
                    mPageToken = it
                }
            }.addOnFailureListener {
                logError(TAG, "error", it)
            }
    }

    private fun handleEvents() {
        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(mLayoutManager) {
            override fun onLoadMore() {
                handleGetImages()
            }
        })
    }

    private fun getUrlFromServer(nameFile: String) {
        mStorageRef.child(nameFile).downloadUrl.addOnSuccessListener {
            mAdapter.addData(Wallpaper(it.toString(), nameFile))
        }.addOnFailureListener {
            logError(TAG, "error", it)
        }
    }

    private fun initData() {
        mAdapter = Adapter(this)
        mLayoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = mLayoutManager
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        ContextCompat.getDrawable(this, R.drawable.item_decoration).notNull {
            itemDecorator.setDrawable(it)
        }
        recyclerView.addItemDecoration(itemDecorator)
        mAdapter.setOnItemClickListener(this)
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
