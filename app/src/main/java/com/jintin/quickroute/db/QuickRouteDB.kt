package com.jintin.quickroute.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.jintin.quickroute.data.Action

@Database(entities = [Action::class], version = 1, exportSchema = true)
abstract class QuickRouteDB : RoomDatabase() {

    companion object {
        const val NAME = "QuickRouteDB"
    }

    abstract fun actionDao(): ActionDao
}