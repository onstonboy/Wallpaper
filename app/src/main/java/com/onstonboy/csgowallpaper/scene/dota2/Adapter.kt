package com.onstonboy.csgowallpaper.scene.dota2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onstonboy.csgowallpaper.R
import com.onstonboy.csgowallpaper.base.LoadMoreRecyclerViewAdapter
import com.onstonboy.csgowallpaper.extension.loadUrlWithPlaceHolder
import kotlinx.android.synthetic.main.item_grid_image.view.*

class Adapter(context: Context) : LoadMoreRecyclerViewAdapter<String>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid_image, parent, false)
        if (ITEM_IMAGE == viewType) {
            return ItemViewHolder(view)
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == ITEM_IMAGE) {
            (holder as ItemViewHolder).bind(mDataList[position])
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun getCustomItemViewType(position: Int): Int {
        return ITEM_IMAGE
    }

    fun addData(url: String) {
        mDataList.add(url)
        notifyItemInserted(mDataList.size)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(url: String) {
            itemView.imageView.loadUrlWithPlaceHolder(url, R.drawable.ic_photo_placeholder)
        }
    }

    companion object {
        private const val ITEM_IMAGE = 0
    }
}
