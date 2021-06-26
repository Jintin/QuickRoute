package com.jintin.quickroute.data

import android.graphics.drawable.Drawable
import com.jintin.quickroute.base.ItemComparable

data class AppInfo(
    var name: String,
    val packageName: String,
    val icon: Drawable
) : ItemComparable<AppInfo> {

    override fun areItemsTheSame(target: AppInfo) = this.packageName == target.packageName

    override fun areContentsTheSame(target: AppInfo) = this == target

}