package com.jintin.quickroute.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    suspend fun insert(data: T)

    @Delete
    suspend fun delete(data: T)

    @Update
    suspend fun update(data: T)
}