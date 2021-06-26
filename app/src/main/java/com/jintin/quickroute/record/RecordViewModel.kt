package com.jintin.quickroute.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jintin.quickroute.data.Record
import com.jintin.quickroute.db.RecordDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordDao: RecordDao
) : ViewModel() {
    val liveData = recordDao.list()

    fun delete(data: Record) {
        viewModelScope.launch {
            recordDao.delete(data)
        }
    }
}