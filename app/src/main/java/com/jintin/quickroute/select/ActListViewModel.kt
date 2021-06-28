package com.jintin.quickroute.select

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jintin.quickroute.data.Action
import com.jintin.quickroute.db.ActionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActListViewModel @Inject constructor(
    app: Application,
    private val actionDao: ActionDao
) : AndroidViewModel(app) {

    private val _liveData = MutableLiveData<List<Action>>()
    val liveData: LiveData<List<Action>>
        get() = _liveData

    fun getList(appName: String, packageName: String) {
        try {
            _liveData.value = getApplication<Application>()
                .packageManager
                .getPackageInfo(
                    packageName,
                    PackageManager.GET_ACTIVITIES
                ).activities
                .filter { it.exported }
                .map {
                    Action(appName, it.name, it.packageName)
                }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    fun add(action: Action) {
        viewModelScope.launch {
            actionDao.insert(action)
        }
    }
}