package com.ezatpanah.hilt_retrofit_paging_youtube.Contact.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Adapter.APIModelAdapterAgency
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Adapter.APIModelAdapterSearch
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Model.ContactSearchModel
import com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Model.ProvinceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactSearchViewModel @Inject constructor(
    private val repository: ApiRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<StateController>(StateController.Empty)
    val state: StateFlow<StateController> = _state

    sealed class StateController {
        object Loading : StateController()
        object Empty : StateController()
        data class Error(val message: String) : StateController()
        data class Success(val data: String) : StateController()

    }

    lateinit var apiModelAdapterSearchProvince: APIModelAdapterSearch
    lateinit var apiModelAdapterAgency: APIModelAdapterAgency

    fun getDataFromApiProvinceSearchDestination(search: String) = viewModelScope.launch { // คนปกติ
        _state.value = StateController.Loading
        val response = repository.contactSearch(search)
        if (response.isSuccessful) {
            if (response.body()!!.data.isNotEmpty()) {
                _state.value = StateController.Success("OK")
                showResult(response.body()!!)
            }else{
                _state.value = StateController.Empty
            }
        }else{
            _state.value = StateController.Error("FAIL")
        }
    }


    fun getDataFromApiProvinceSearchVideophones(search: String) = viewModelScope.launch { // หน่วยงาน
        _state.value = StateController.Loading
        val response = repository.searchAgency(search)
        if (response.isSuccessful){
            if (response.body()!!.status == "OK"){
                _state.value = StateController.Success("OK")
                showResultAgency(response.body()!!)
            }else{
                _state.value = StateController.Empty
            }
        }else{
            _state.value = StateController.Empty
        }
//        ApiService.endpointagencysearch.searchAgency(
//            search,
//            "100"
//        ).enqueue(object : retrofit2.Callback<ProvinceModel> {
//            override fun onResponse(
//                call: Call<ProvinceModel>,
//                response: Response<ProvinceModel>
//            ) {
//                if (response.isSuccessful) {
//                    if (response.body()!!.data.isNotEmpty()) {
//                        Log.i("vdosuc", "onResponse: video suc")
//                        _state.value = StateController.Success("OK")
//                        showResultAgency(response.body()!!)
//                    } else {
//                        _state.value = StateController.Empty
//                    }
//
//                } else {
//                    _state.value = StateController.Empty
//                }
//            }
//
//            override fun onFailure(call: Call<ProvinceModel>, t: Throwable) {
//                _state.value = StateController.Empty
//
//            }
//
//
//        })
    }

    private fun showResult(results: ContactSearchModel) {
        apiModelAdapterSearchProvince.setData(results.data)
    }

    private fun showResultAgency(results: ProvinceModel) {
        apiModelAdapterAgency.setData(results.data)
    }
}