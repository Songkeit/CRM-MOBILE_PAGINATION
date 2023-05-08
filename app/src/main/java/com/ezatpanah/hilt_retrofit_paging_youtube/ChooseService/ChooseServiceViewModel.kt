package com.ezatpanah.hilt_retrofit_paging_youtube.ChooseService

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.ChooseService.Model.SeatsModelApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChooseServiceViewModel @Inject constructor(
    private val repository: ApiRepository,
) :
    ViewModel() {
    private val _stateseat =
        MutableStateFlow<StateControllerSeat>(StateControllerSeat.Empty)
    val stateseat: StateFlow<StateControllerSeat> = _stateseat

    sealed class StateControllerSeat {
        object Loading : StateControllerSeat()
        object Empty : StateControllerSeat()
        data class Error(val message: String) : StateControllerSeat()
        data class Success(val dataList: SeatsModelApi?) : StateControllerSeat()
    }

    fun seatsVrs() = viewModelScope.launch {
        val response = repository.seatsVrs()
        if (response.isSuccessful) {
            if (response.body()!!.status == "OK") {
                _stateseat.value = StateControllerSeat.Success(response.body())
            } else {
                _stateseat.value = StateControllerSeat.Error("FAIL")

            }
        } else {
            _stateseat.value = StateControllerSeat.Error("FAIL")
        }
        _stateseat.value = StateControllerSeat.Loading
    }
}