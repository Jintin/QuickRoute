package com.jintin.quickroute.action

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jintin.quickroute.data.Action
import com.jintin.quickroute.db.ActionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionListViewModel @Inject constructor(
    private val actionDao: ActionDao
) : ViewModel() {
    val liveData = actionDao.list()

    fun delete(data: Action) {
        viewModelScope.launch {
            actionDao.delete(data)
        }
    }
}