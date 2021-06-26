package com.jintin.quickroute.base

import androidx.recyclerview.widget.DiffUtil

class ItemCallback<T : ItemComparable<T>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}