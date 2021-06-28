package com.jintin.quickroute.extra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jintin.quickroute.data.Extra
import javax.inject.Inject

class EditDialogViewModel @Inject constructor() : ViewModel() {

    private val _validateLiveData = MutableLiveData<Boolean>()
    val validateLiveData: LiveData<Boolean>
        get() = _validateLiveData

    private var name: String? = null
    private var type: Extra.Type? = null
    private var value: String? = null

    fun setExtra(extra: Extra?) {
        name = extra?.name
        type = extra?.type
        value = extra?.value
        validate()
    }

    fun setName(value: String) {
        this.name = value
        validate()
    }

    fun setType(value: String) {
        this.type = Extra.Type.getType(value)
        validate()
    }

    fun setValue(value: String) {
        this.value = value
        validate()
    }

    fun validate() {
        if (name == null || name?.length == 0) {
            _validateLiveData.value = false
            return
        }
        _validateLiveData.value = when (type) {
            Extra.Type.STRING -> {
                value != null && value?.length != 0
            }
            Extra.Type.INT -> {
                value?.toIntOrNull() != null
            }
            Extra.Type.FLOAT -> {
                value?.toFloatOrNull() != null
            }
            null -> false
        }
    }
}