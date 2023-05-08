package com.ezatpanah.hilt_retrofit_paging_youtube.Unsaved.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Model.ApiInformationUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.KioskList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Model.DeleteData
import com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.Unsaved.Paging.UnsavedPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnsavesViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val unsavedList = Pager(PagingConfig(1)) {
        UnsavedPagingSource(repository) // search
    }.flow.cachedIn(viewModelScope)


    private val _stateintent = MutableStateFlow<StateControllerIntent>(StateControllerIntent.Empty)
    val stateIntent: StateFlow<StateControllerIntent> = _stateintent
    sealed class StateControllerIntent {
        object Loading : StateControllerIntent()

        object Empty : StateControllerIntent()
        data class Error(val message: String) : StateControllerIntent()
        data class Success(val id: Int) : StateControllerIntent()


    }

    fun getDataCheckIntent(id: Int) = viewModelScope.launch {// see data
        _stateintent.value = StateControllerIntent.Loading
        val response = repository.getUserDetails(id)
        if (response.isSuccessful && response.body()!!.status == "OK") {
            _stateintent.value = StateControllerIntent.Success(id)
            Log.i("check intent", "getDataCheckIntent: success")
        }else{
            _stateintent.value = StateControllerIntent.Error("Error")
            Log.i("check intent", "getDataCheckIntent: error")

        }
    }
}