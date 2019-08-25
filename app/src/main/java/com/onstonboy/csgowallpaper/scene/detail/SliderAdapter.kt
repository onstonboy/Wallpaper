package com.onstonboy.csgowallpaper.scene.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onstonboy.csgowallpaper.R
import com.onstonboy.csgowallpaper.base.BaseRecyclerViewAdapter
import com.onstonboy.csgowallpaper.extension.loadUrlWithPlaceHolder
import com.onstonboy.csgowallpaper.model.Wallpaper
import kotlinx.android.synthetic.main.item_slider.view.*

class SliderAdapter(context: Context) :
    BaseRecyclerViewAdapter<Wallpaper, SliderAdapter.ItemViewHolder>(context) {

    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return ItemViewHolder(view, mOnItemClickListener)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(mDataList[position])
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
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
}
