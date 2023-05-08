package com.ezatpanah.hilt_retrofit_paging_youtube.Home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Model.ApiInformationUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.CreateUser
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
class MainInformationViewModel @Inject constructor(
    private val repository: ApiRepository,
) :
    ViewModel() {
    val loading = MutableLiveData<Boolean>()
    private val _stateCreate = MutableStateFlow<StateControllerCreate>(StateControllerCreate.Empty)
    val stateCreate: StateFlow<StateControllerCreate> = _stateCreate

    sealed class StateControllerCreate {
        object Loading : StateControllerCreate()
        object Empty : StateControllerCreate()
        data class Error(val message: String) : StateControllerCreate()
        data class Success(val data: String) : StateControllerCreate()
    }

    private val _stateDetail = MutableStateFlow<StateController>(StateController.Empty)
    val stateDetail: StateFlow<StateController> = _stateDetail

    sealed class StateController {
        object Loading : StateController()
        object Empty : StateController()
        data class Error(val message: String) : StateController()
        data class DataNull(val message: String) : StateController()
        data class Success(val data: ApiInformationUser.Data?) : StateController()
    }

    private val _statetypeStory =
        MutableStateFlow<StateControllerTypeStory>(StateControllerTypeStory.Empty)
    val statetypeStory: StateFlow<StateControllerTypeStory> = _statetypeStory

    sealed class StateControllerTypeStory {
        object Loading : StateControllerTypeStory()
        object Empty : StateControllerTypeStory()
        data class Error(val message: String) : StateControllerTypeStory()
        data class Success(val dataList: TypeStoryModel?) : StateControllerTypeStory()

    }

    private val _stateservicelist =
        MutableStateFlow<StateControllerServiceList>(StateControllerServiceList.Empty)
    val stateservicelist: StateFlow<StateControllerServiceList> = _stateservicelist

    sealed class StateControllerServiceList {
        object Loading : StateControllerServiceList()
        object Empty : StateControllerServiceList()
        data class Error(val message: String) : StateControllerServiceList()
        data class Success(val dataList: ServiceList?) : StateControllerServiceList()
    }

    private val _statekiosk = MutableStateFlow<StateControllerKiosk>(StateControllerKiosk.Empty)
    val statekiosk: StateFlow<StateControllerKiosk> = _statekiosk

    sealed class StateControllerKiosk {
        object Loading : StateControllerKiosk()
        object Empty : StateControllerKiosk()
        data class Error(val message: String) : StateControllerKiosk()
        data class Success(val dataList: KioskList?) : StateControllerKiosk()

    }

    private val _stateUpdate = MutableStateFlow<StateControllerUpdate>(StateControllerUpdate.Empty)
    val stateUpdate: StateFlow<StateControllerUpdate> = _stateUpdate

    sealed class StateControllerUpdate {
        object Loading : StateControllerUpdate()
        object Empty : StateControllerUpdate()
        data class Error(val message: String) : StateControllerUpdate()
        data class Success(val data: String) : StateControllerUpdate()
    }

    fun loadDetailUser(id: Int) = viewModelScope.launch {

        val response = repository.getUserDetails(id)
        if (response.isSuccessful && response.body()!!.status == "OK") {
            _stateDetail.value = StateController.Success(response.body()!!.data)

        } else {
            _stateDetail.value = StateController.Error("FAIL")
        }


    }

    fun typeStory() = viewModelScope.launch {
        val response = repository.typeStory()
        if (response.isSuccessful) {
            if (response.body()!!.status == "OK") {
                _statetypeStory.value = StateControllerTypeStory.Success(response.body())
            } else {
                _statetypeStory.value = StateControllerTypeStory.Error("FAIL")

            }
        }
    }

    fun serviceTypeDropDown() = viewModelScope.launch {
        val response = repository.serviceListDropdown()
        if (response.isSuccessful) {
            if (response.body()!!.status == "OK") {
                _stateservicelist.value = StateControllerServiceList.Success(response.body())
            } else {
                _stateservicelist.value = StateControllerServiceList.Error("FAIL")

            }
        } else {
            _stateservicelist.value = StateControllerServiceList.Error("FAIL")
        }
        _stateservicelist.value = StateControllerServiceList.Loading
    }

    fun kioskDropDown() = viewModelScope.launch {
        val response = repository.kioskDropdown()
        if (response.isSuccessful) {
            if (response.body()!!.status == "OK") {
                _statekiosk.value = StateControllerKiosk.Success(response.body())

            } else {
                _statekiosk.value = StateControllerKiosk.Error("FAIL")


            }
        } else {
            _statekiosk.value = StateControllerKiosk.Error("FAIL")
        }
        _statekiosk.value = StateControllerKiosk.Loading
    }

    fun updateData(updateUser: ApiInformationUser.Data) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.updateData(updateUser)
        if (response.isSuccessful && response.body()!!.status != "FAIL") {
            _stateUpdate.value = StateControllerUpdate.Success("บันทึกข้อมูลสำเร็จ")
        } else {
            _stateUpdate.value = StateControllerUpdate.Error("บันทึกข้อมูลไม่สำเร็จ")
        }
    }

    fun createData(createUser: CreateUser.Data) = viewModelScope.launch {

        val response = repository.createData(createUser)
        if (response.isSuccessful && response.body()!!.status != "FAIL") {
            _stateCreate.value = StateControllerCreate.Success("บันทึกข้อมูลสำเร็จ")

        } else {
            _stateCreate.value = StateControllerCreate.Error("บันทึกข้อมูลไม่สำเร็จ")

        }

    }
}