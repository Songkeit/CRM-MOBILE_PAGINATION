package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.MainInformationViewModel
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentChooseServiceBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentEmergencyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class ChooseService : AppCompatActivity() {
    var listServiceSeatsDropDown = mutableListOf<String>()
    var listSerivceSeatsMapOfDropDown = mutableMapOf<String, String>() // name:id
    private var serviceVrs:AutoCompleteTextView? = null
    private val viewModel: ChooseServiceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_choose_service)
        serviceVrs = findViewById(R.id.select_service_vrs)
        seatDropDown()
    }

    private fun seatDropDown() {
        viewModel.seatsVrs()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.stateseat.collect {
                when (it) {
                    is ChooseServiceViewModel.StateControllerSeat.Success -> {
                        appendServiceSeatsDropDown(it.dataList!!)
                        dropDownSeatsAdapter()
                    }
                    else -> {}
                }
            }
        }

    }

    private fun appendServiceSeatsDropDown(data: SeatsModelApi) {
        listServiceSeatsDropDown.clear()
        listSerivceSeatsMapOfDropDown.clear()
        var serviceSeatsMax = data.data.size
        for (i in 0 until serviceSeatsMax) {
            listServiceSeatsDropDown.add(data.data[i].name.toString())
            listSerivceSeatsMapOfDropDown[data.data[i].name.toString()] =
                data.data[i].id.toString()
        }

    }

    private fun dropDownSeatsAdapter() {

        var adapterServiceSeats: ArrayAdapter<String>? =
            ArrayAdapter(this, R.layout.list_items, listServiceSeatsDropDown)
        serviceVrs!!.setAdapter(adapterServiceSeats)
        serviceVrs!!.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                var itemSelect =
                    adapterServiceSeats!!.getItem(position).toString() // เลือกdropdown name
                Toast.makeText(this, "$itemSelect", Toast.LENGTH_SHORT).show()
                Log.i(
                    "check",
                    "dropDownSeatsAdapter: ${listSerivceSeatsMapOfDropDown[itemSelect]}"
                ) // id
                var btnsrv: Button = findViewById(R.id.btn_srv_vrs)
                btnsrv.setOnClickListener {
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}