package com.jintin.quickroute.extra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jintin.quickroute.data.Action
import com.jintin.quickroute.data.Extra
import com.jintin.quickroute.db.ActionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtraListViewModel @Inject constructor(private val actionDao: ActionDao) : ViewModel() {

    private val _liveData = MutableLiveData<List<Extra>>()
    val liveData: LiveData<List<Extra>>
        get() = _liveData
    private lateinit var data: Action
    private var newCreate: Boolean = false
    private var list = mutableListOf<Extra>()

    fun setData(data: Action, newCreate: Boolean) {
        this.data = data
        this.newCreate = newCreate
        list = data.extras.toMutableList()
        setValue()
    }

    fun applyAction(afterAction: () -> Unit) {
        viewModelScope.launch {
            data.copy(extras = list).apply {
                if (newCreate) {
                    actionDao.insert(this)
                } else {
                    actionDao.update(this)
                }
                afterAction()
            }
        }
    }

    fun remove(extra: Extra) {
        list.remove(extra)
        setValue()
    }

    fun add(extra: Extra) {
        list.add(extra)
        setValue()
    }

    fun update(oldData: Extra, newData: Extra) {
        list[list.indexOf(oldData)] = newData
        setValue()
    }

    fun setValue() {
        _liveData.value = list.toList()
    }
}