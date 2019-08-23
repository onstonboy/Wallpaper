package com.onstonboy.csgowallpaper.scene.dota2

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.onstonboy.csgowallpaper.BaseActivity
import com.onstonboy.csgowallpaper.R
import com.onstonboy.csgowallpaper.extension.notNull
import kotlinx.android.synthetic.main.activity_dota2_package.*

class Dota2PackageActivity : BaseActivity() {

    private var mStorageRef = FirebaseStorage.getInstance().reference
    private lateinit var mAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dota2_package)

        initData()
        handleGetImages()
    }

    private fun handleGetImages() {
        getImagesFromServer("3_heroes_loadingscreen.png")
        getImagesFromServer("abaddon_endless_night_ls.png")
        getImagesFromServer("absolute_zero_absolute_zero_loadingscreen.png")
        getImagesFromServer("abyss_tyrant_abyss_tyrant_loadingscreen.png")
        getImagesFromServer("aeol_drias_loading_screen.png")
        getImagesFromServer("aesir_lord_odin_loadingscreen.png")
        getImagesFromServer("alchemist_convicts_trophy_loading_screen.png")
        getImagesFromServer("alchemist_jungle_chief_loadingscreen.png")
    }

    private fun getImagesFromServer(url: String) {
        mStorageRef.child(url).downloadUrl.addOnSuccessListener {
            mAdapter.addData(it.toString())
        }.addOnFailureListener {
            logError(TAG, "error", it)
        }
    }

    private fun initData() {
        mAdapter = Adapter()
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = layoutManager
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        ContextCompat.getDrawable(this, R.drawable.item_decoration).notNull {
            itemDecorator.setDrawable(it)
        }
        recyclerView.addItemDecoration(itemDecorator)
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
