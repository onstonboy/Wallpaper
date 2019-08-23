package com.onstonboy.csgowallpaper.scene.dota2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onstonboy.csgowallpaper.R
import com.onstonboy.csgowallpaper.extension.loadUrlWithPlaceHolder
import kotlinx.android.synthetic.main.item_grid_image.view.*

class Adapter : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    private var mDataList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid_image, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mDataList[position])
    }

    fun addData(url: String) {
        mDataList.add(url)
        notifyItemInserted(mDataList.size)
    }

    fun updateData(urls: List<String>) {
        mDataList.addAll(urls)
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(url: String) {
            itemView.imageView.loadUrlWithPlaceHolder(url, R.drawable.ic_photo_placeholder)
        }
    }
}