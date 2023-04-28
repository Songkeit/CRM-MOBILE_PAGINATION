package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Model.ApiInformationUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Paging.EmergencyPagingSource
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.KioskList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi.ApiRepository
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


    private val _state = MutableStateFlow<StateController>(StateController.Empty)
    val state: StateFlow<StateController> = _state
    sealed class StateController {
        object Loading : StateController()
        object Empty : StateController()
        data class Error(val message: String) : StateController()
        data class Success(val status: String) : StateController()
        data class Data(val data: DeleteData?) : StateController()


    }
    private val _stateintent = MutableStateFlow<StateControllerIntent>(StateControllerIntent.Empty)
    val stateIntent: StateFlow<StateControllerIntent> = _stateintent
    sealed class StateControllerIntent {
        object Loading : StateControllerIntent()

        object Empty : StateControllerIntent()
        data class Error(val message: String) : StateControllerIntent()
        data class Success(val id: Int) : StateControllerIntent()


    }
    private val _stateDelete = MutableStateFlow<StateControllerDelete>(StateControllerDelete.Empty)
    val stateDelete: StateFlow<StateControllerDelete> = _stateDelete
    sealed class StateControllerDelete {
        object Loading : StateControllerDelete()

        object Empty : StateControllerDelete()
        data class Error(val message: String) : StateControllerDelete()
        data class Success(val id: Int) : StateControllerDelete()


    }
    private val _statetypeStory = MutableStateFlow<StateControllerTypeStory>(StateControllerTypeStory.Empty)
    val statetypeStory: StateFlow<StateControllerTypeStory> = _statetypeStory
    sealed class StateControllerTypeStory{
        object Loading : StateControllerTypeStory()
        object Empty : StateControllerTypeStory()
        data class Error(val message: String) : StateControllerTypeStory()
        data class Success(val dataList: TypeStoryModel?) : StateControllerTypeStory()
        data class Data(val data: DeleteData?) : StateControllerTypeStory()
    }
    private val _stateservicelist = MutableStateFlow<StateControllerServiceList>(StateControllerServiceList.Empty)
    val stateservicelist: StateFlow<StateControllerServiceList> = _stateservicelist
    sealed class StateControllerServiceList {
        object Loading : StateControllerServiceList()
        object Empty : StateControllerServiceList()
        data class Error(val message: String) : StateControllerServiceList()
        data class Success(val dataList: ServiceList?) : StateControllerServiceList()
        data class Data(val data: DeleteData?) : StateControllerServiceList()
    }
    private val _statekiosk = MutableStateFlow<StateControllerKiosk>(StateControllerKiosk.Empty)
    val statekiosk: StateFlow<StateControllerKiosk> = _statekiosk
    sealed class StateControllerKiosk{
        object Loading : StateControllerKiosk()
        object Empty : StateControllerKiosk()
        data class Error(val message: String) : StateControllerKiosk()
        data class Success(val dataList: KioskList?) : StateControllerKiosk()
        data class Data(val data: DeleteData?) : StateControllerKiosk()
    }
    private val _stateUpdate = MutableStateFlow<StateControllerUpdate>(StateControllerUpdate.Empty)
    val stateUpdate: StateFlow<StateControllerUpdate> = _stateUpdate
    sealed class StateControllerUpdate {
        object Loading : StateControllerUpdate()
        object Empty : StateControllerUpdate()
        data class Error(val message: String) : StateControllerUpdate()
        data class Success(val data: String) : StateControllerUpdate()
    }


    //Api
    val detailsEmergency = MutableLiveData<ApiInformationUser.Data?>()
    fun loadDetailsMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getUserDetails(id)
        if (response.isSuccessful) {
            detailsEmergency.postValue(response.body()!!.data)
        }
        loading.postValue(false)
    }


    fun deleteDataApi(userData: DeleteData.Data) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.deleteEmergency(userData)
        if (response.isSuccessful){
            if (response.body()!!.status == "OK") {
                _state.value = StateController.Success("OK")
            } else {
                _state.value = StateController.Error("FAIL")
            }
        }else{
            _state.value = StateController.Error("FAIL")

        }
    }
    fun getMovieDetail(id: Int) = viewModelScope.launch {// see data
        loading.postValue(true)
        val response = repository.getUserDetails(id)
        if (response.isSuccessful && response.body()!!.status == "OK") {
            _state.value = StateController.Success("OK")
        }else{
            _state.value = StateController.Error("Error")
        }
        loading.postValue(false)
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
    fun getDataCheckDelete(id: Int) = viewModelScope.launch {// see data
        _stateDelete.value = StateControllerDelete.Loading
        val response = repository.getUserDetails(id)
        if (response.isSuccessful && response.body()!!.status == "OK") {
            _stateDelete.value = StateControllerDelete.Success(id)
            Log.i("check intent", "getDataCheckIntent: success")
        }else{
            _stateDelete.value = StateControllerDelete.Error("Error")
            Log.i("check intent", "getDataCheckIntent: error")

        }
    }
    fun typeStory() = viewModelScope.launch {// ข้อมูลที่ถูกส่งไปที่ที่ detail
        loading.postValue(true)
        val response = repository.typeStory()
        if (response.isSuccessful) {
            if (response.body()!!.status == "OK") {
                _statetypeStory.value = StateControllerTypeStory.Success(response.body())
            } else {
                _statetypeStory.value = StateControllerTypeStory.Error("FAIL")

            }
        }
        loading.postValue(false)
    }
    fun serviceTypeDropDown() = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.serviceListDropdown()
        if (response.isSuccessful){
            if (response.body()!!.status == "OK") {
                _stateservicelist.value = StateControllerServiceList.Success(response.body())
            } else {
                _stateservicelist.value = StateControllerServiceList.Error("FAIL")
            }
        }else{
            _stateservicelist.value = StateControllerServiceList.Error("FAIL")
        }
        loading.postValue(true)
    }
    fun kioskDropDown() = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.kioskDropdown()
        if (response.isSuccessful){
            if (response.body()!!.status == "OK") {
                _statekiosk.value = StateControllerKiosk.Success(response.body())
            } else {
                _statekiosk.value = StateControllerKiosk.Error("FAIL")

            }
        }else{
            _statekiosk.value = StateControllerKiosk.Error("FAIL")
        }
        loading.postValue(true)
    }
    fun updateData(updateUser: ApiInformationUser.Data) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.updateData(updateUser)
        if (response.isSuccessful && response.body()!!.status != "FAIL"){
            _stateUpdate.value = StateControllerUpdate.Success("บันทึกข้อมูลสำเร็จ")
        }else{
            _stateUpdate.value = StateControllerUpdate.Error("บันทึกข้อมูลไม่สำเร็จ")
        }
        loading.postValue(false)
        Log.i("update", "updateData: update data success")

    }
}