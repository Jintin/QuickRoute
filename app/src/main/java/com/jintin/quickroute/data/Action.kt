package com.jintin.quickroute.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jintin.quickroute.base.ItemComparable
import com.jintin.quickroute.db.ActionConverter
import kotlinx.parcelize.Parcelize

@TypeConverters(ActionConverter::class)
@Parcelize
@Entity(tableName = "action")
data class Action(
    val name: String,
    val actName: String,
    val packageName: String,
    val extras: List<Extra> = listOf(),
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
) : ItemComparable<Action>, Parcelable {

    override fun areItemsTheSame(target: Action): Boolean {
        return actName == target.actName
    }

    override fun areContentsTheSame(target: Action): Boolean {
        return this == target
    }

}