package com.onstonboy.csgowallpaper.scene.imagepackage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onstonboy.csgowallpaper.R
import com.onstonboy.csgowallpaper.base.LoadMoreRecyclerViewAdapter
import com.onstonboy.csgowallpaper.extension.loadUrlWithPlaceHolder
import com.onstonboy.csgowallpaper.model.Wallpaper
import kotlinx.android.synthetic.main.item_grid_image.view.*

class Adapter(context: Context) : LoadMoreRecyclerViewAdapter<Wallpaper>(context) {

    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid_image, parent, false)
        if (ITEM_IMAGE == viewType) {
            return ItemViewHolder(view, mOnItemClickListener)
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

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    fun addData(wallpaper: Wallpaper) {
        mDataList.add(wallpaper)
        notifyItemInserted(mDataList.size)
    }

    class ItemViewHolder(
        itemView: View,
        onItemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView) {

        private var mOnItemClickListener = onItemClickListener

        fun bind(wallpaper: Wallpaper) {
            itemView.imageView.loadUrlWithPlaceHolder(
                wallpaper.url,
                R.drawable.ic_photo_placeholder
            )
            itemView.imageView.setOnClickListener {
                mOnItemClickListener?.onItemClick(wallpaper)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(wallpaper: Wallpaper)
    }

    companion object {
        private const val ITEM_IMAGE = 0
    }
}
