package com.jintin.quickroute.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jintin.quickroute.base.BaseDao
import com.jintin.quickroute.data.Action

@Dao
interface ActionDao : BaseDao<Action> {

    @Query("SELECT * FROM `action`")
    fun list(): LiveData<List<Action>>

}