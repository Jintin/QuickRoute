package com.jintin.quickroute.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jintin.quickroute.base.BaseDao
import com.jintin.quickroute.data.Record

@Dao
interface RecordDao : BaseDao<Record> {

    @Query("SELECT * FROM record")
    fun list(): LiveData<List<Record>>

}