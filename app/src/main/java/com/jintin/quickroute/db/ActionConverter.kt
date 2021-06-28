package com.jintin.quickroute.db

import androidx.room.TypeConverter
import com.jintin.quickroute.data.Extra
import org.json.JSONArray

class ActionConverter {

    @TypeConverter
    fun convert(list: List<Extra>): String {
        val array = JSONArray()
        list.forEach {
            array.put("${it.name},${it.value},${it.type.ordinal}")
        }
        return array.toString()
    }

    @TypeConverter
    fun convert(string: String): List<Extra> {
        val array = JSONArray(string)
        val list = mutableListOf<Extra>()
        for (i in 0 until array.length()) {
            val values = array.optString(i, "")
                .split(",")
            list.add(Extra(values[0], values[1], Extra.Type.values()[values[2].toInt()]))
        }
        return list
    }

}