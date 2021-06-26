package com.jintin.quickroute.data

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jintin.quickroute.base.ItemComparable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "record")
data class Record(
    val name: String,
    val actName: String,
    val packageName: String
) : ItemComparable<Record>, Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun areItemsTheSame(target: Record): Boolean {
        return actName == target.actName
    }

    override fun areContentsTheSame(target: Record): Boolean {
        return this == target
    }

    suspend fun getIcon(packageManager: PackageManager): Drawable = withContext(Dispatchers.IO) {
        packageManager.getApplicationIcon(packageName)
    }

}