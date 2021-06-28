package com.jintin.quickroute.data

import android.os.Parcelable
import com.jintin.quickroute.base.ItemComparable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Extra(
    val name: String,
    val value: String,
    val type: Type
) : Parcelable, ItemComparable<Extra> {

    enum class Type {
        STRING, INT, FLOAT;

        val label: String
            get() = when (this) {
                STRING -> "String"
                INT -> "Int"
                FLOAT -> "Float"
            }

        companion object {
            fun getType(string: String): Type? {
                return when (string) {
                    "String" -> STRING
                    "Int" -> INT
                    "Float" -> FLOAT
                    else -> null
                }
            }
        }
    }

    override fun areItemsTheSame(target: Extra): Boolean {
        return name == target.name
    }

    override fun areContentsTheSame(target: Extra): Boolean {
        return this == target
    }
}