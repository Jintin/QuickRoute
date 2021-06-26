package com.jintin.quickroute.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.Adapter<*>.bindEmptyView(view: View) {
    fun updateEmptyViewVisibility() {
        view.visibility = if (itemCount == 0) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
    updateEmptyViewVisibility()
    registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            updateEmptyViewVisibility()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            updateEmptyViewVisibility()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            updateEmptyViewVisibility()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            updateEmptyViewVisibility()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            updateEmptyViewVisibility()
        }
    })

}