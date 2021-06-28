package com.jintin.quickroute.select

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jintin.quickroute.data.AppInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(app: Application) : AndroidViewModel(app) {

    private val _liveDate = MutableLiveData<List<AppInfo>>()
    val liveData: LiveData<List<AppInfo>>
        get() = _liveDate

    init {
        viewModelScope.launch {
            _liveDate.value = getList(app.packageManager)
        }
    }

    private suspend fun getList(packageManager: PackageManager) = withContext(Dispatchers.IO) {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
            .parallelMap {
                AppInfo(
                    packageManager.getApplicationLabel(it).toString(),
                    it.packageName,
                )
            }
    }


    private suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> =
        coroutineScope {
            map { async { f(it) } }.awaitAll()
        }
}