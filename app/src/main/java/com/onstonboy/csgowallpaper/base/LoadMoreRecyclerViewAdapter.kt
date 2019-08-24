package com.onstonboy.csgowallpaper.base

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.onstonboy.csgowallpaper.R

abstract class LoadMoreRecyclerViewAdapter<T> protected constructor(
    context: Context?
) : BaseRecyclerViewAdapter<T, RecyclerView.ViewHolder>(context) {
    private var mIsLoadMore: Boolean = false
    private val mHandler = Handler()
    private var mRunnable: Runnable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_PROGRESS -> {
                val view: View = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_bottom_recycler_view, parent, false
                )
                return ItemBottomViewHolder(view)
            }
        }
        throw RuntimeException("LoadMoreRecyclerViewAdapter: ViewHolder = null")
    }

    override fun getItemViewType(position: Int): Int {
        return if (mIsLoadMore && position == bottomItemPosition()) {
            TYPE_PROGRESS
        } else getCustomItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_PROGRESS) {
            (holder as ItemBottomViewHolder).bind(mIsLoadMore)
        }
    }

    private fun bottomItemPosition(): Int {
        return itemCount - 1
    }

    fun onStartLoadMore() {
        mIsLoadMore = true
        mRunnable = Runnable { notifyItemChanged(bottomItemPosition()) }
        mHandler.post(mRunnable)
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable)
        }
    }

    fun onStopLoadMore() {
        mIsLoadMore = false
        mRunnable = Runnable { notifyItemChanged(bottomItemPosition(), mDataList) }
        mHandler.post(mRunnable)
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable)
        }
    }

    private class ItemBottomViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(
        view
    ) {

        private var progressBar: ProgressBar = view.findViewById(R.id.loadMoreProgressBar)

        internal fun bind(isLoadMore: Boolean) {
            if (isLoadMore) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    protected abstract fun getCustomItemViewType(position: Int): Int

    fun onClearCallBackLoadMore() {
        mHandler.removeCallbacks(mRunnable)
    }

    companion object {
        private const val TYPE_PROGRESS = 0xFFFF
    }
}
