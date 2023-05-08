package com.ezatpanah.hilt_retrofit_paging_youtube.Home.HomeViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.CreateUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.KioskList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Model.DeleteData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel()  {
    val loading = MutableLiveData<Boolean>()
    private val _stateCreate = MutableStateFlow<StateControllerCreate>(StateControllerCreate.Empty)
    val stateCreate: StateFlow<StateControllerCreate> = _stateCreate
    sealed class StateControllerCreate {
        object Loading : StateControllerCreate()
        object Empty : StateControllerCreate()
        data class Error(val message: String) : StateControllerCreate()
        data class Success(val data: String) : StateControllerCreate()
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
    private val _statetypeStory = MutableStateFlow<StateControllerTypeStory>(StateControllerTypeStory.Empty)
    val statetypeStory: StateFlow<StateControllerTypeStory> = _statetypeStory
    sealed class StateControllerTypeStory{
        object Loading : StateControllerTypeStory()
        object Empty : StateControllerTypeStory()
        data class Error(val message: String) : StateControllerTypeStory()
        data class Success(val dataList: TypeStoryModel?) : StateControllerTypeStory()
        data class Data(val data: DeleteData?) : StateControllerTypeStory()
    }
    fun createUserData(user: CreateUser.Data) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.createData(user)
        if (response.isSuccessful && response.body()!!.status != "FAIL"){
            _stateCreate.value = StateControllerCreate.Success("บันทึกข้อมูลสำเร็จ")
        }else{
            _stateCreate.value = StateControllerCreate.Error("บันทึกข้อมูลไม่สำเร็จ")
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
}