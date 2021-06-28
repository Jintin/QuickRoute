package com.jintin.quickroute.base

import android.content.pm.PackageManager
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun PackageManager.loadImage(imageView: ImageView, packageName: String) {
    imageView.setImageDrawable(null)
    imageView.setImageDrawable(
        withContext(Dispatchers.IO) {
            getApplicationIcon(packageName)
        })
}