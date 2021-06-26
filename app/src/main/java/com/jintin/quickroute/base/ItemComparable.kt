package com.jintin.quickroute.base

interface ItemComparable<T> {
    fun areItemsTheSame(target: T): Boolean

    fun areContentsTheSame(target: T): Boolean
}