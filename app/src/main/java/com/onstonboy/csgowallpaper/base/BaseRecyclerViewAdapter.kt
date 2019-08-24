package com.onstonboy.csgowallpaper.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.onstonboy.csgowallpaper.extension.notNull

/**
 * Base Adapter.
 *
 * @param <V> is a type extend from [RecyclerView.ViewHolder]
 * @param <T> is a Object
</T></V> */

abstract class BaseRecyclerViewAdapter<T, V : RecyclerView.ViewHolder> protected constructor(
    protected val context: Context?
) : RecyclerView.Adapter<V>() {
    protected var mDataList: MutableList<T> = ArrayList()
    private lateinit var mItemClickListener: OnItemClickListener<T>

    val dataList: List<T>
        get() = mDataList

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun updateData(dataList: List<T>?) {
        if (dataList == null) {
            return
        }
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        mDataList.clear()
    }

    fun removeItem(position: Int) {
        mDataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mDataList.size)
    }

    fun addItem(data: T, position: Int) {
        mDataList.add(position, data)
        notifyItemInserted(position)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener<T>?) {
        itemClickListener.notNull { mItemClickListener = it }
    }

    /**
     * OnItemClickListener
     *
     * @param <T> Data from item click
    </T> */
    interface OnItemClickListener<T> {
        fun onItemRecyclerViewClick(item: T)
    }
}
