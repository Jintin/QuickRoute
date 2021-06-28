package com.jintin.quickroute.data

import com.jintin.quickroute.base.ItemComparable

data class AppInfo(
    var name: String,
    val packageName: String
) : ItemComparable<AppInfo> {

    override fun areItemsTheSame(target: AppInfo) = this.packageName == target.packageName

    override fun areContentsTheSame(target: AppInfo) = this == target

}