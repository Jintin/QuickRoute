package com.jintin.quickroute.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jintin.quickroute.data.Record

@Database(entities = [Record::class], version = 1, exportSchema = true)
abstract class QuickRouteDB : RoomDatabase() {

    companion object {
        const val NAME = "QuickRouteDB"
    }

    abstract fun recordDao(): RecordDao
}