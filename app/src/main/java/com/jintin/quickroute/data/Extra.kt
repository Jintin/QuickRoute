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
        STRING, INT, BYTE, CHAR, LONG, FLOAT, SHORT, DOUBLE, BOOLEAN;

        val label: String
            get() = when (this) {
                STRING -> "String"
                INT -> "Int"
                FLOAT -> "Float"
                BYTE -> "Byte"
                CHAR -> "Char"
                LONG -> "Long"
                SHORT -> "Short"
                DOUBLE -> "Double"
                BOOLEAN -> "Boolean"
            }

        fun isValid(value: String?): Boolean {
            if (value == null) {
                return false
            }
            return when (this) {
                STRING -> value.isNotEmpty()
                INT -> value.toIntOrNull() != null
                FLOAT -> value.toFloatOrNull() != null
                BYTE -> value.toByteOrNull() != null
                CHAR -> value.singleOrNull() != null
                LONG -> value.toLongOrNull() != null
                SHORT -> value.toShortOrNull() != null
                DOUBLE -> value.toDoubleOrNull() != null
                BOOLEAN -> true
            }
        }

        companion object {
            fun getType(string: String): Type? {
                return when (string) {
                    "String" -> STRING
                    "Int" -> INT
                    "Float" -> FLOAT
                    "Byte" -> BYTE
                    "Char" -> CHAR
                    "Long" -> LONG
                    "Short" -> SHORT
                    "Double" -> DOUBLE
                    "Boolean" -> BOOLEAN
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