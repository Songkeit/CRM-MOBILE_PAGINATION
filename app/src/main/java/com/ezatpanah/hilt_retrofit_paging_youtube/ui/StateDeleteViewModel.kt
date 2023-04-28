package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StateDeleteViewModel(application: Application):AndroidViewModel(application) {
    val dataStore = DataStoreManager(application)
    val getDelete = dataStore.getDelete().asLiveData(Dispatchers.IO)

    fun setDelete(isDelete:Boolean){
        viewModelScope.launch {
            dataStore.setDelete(isDelete)
        }
    }
}