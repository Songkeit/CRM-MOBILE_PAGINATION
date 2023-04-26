package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.hilt_retrofit_paging_youtube.Component.showCustomToast
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Adapter.LoadMoreAdapter
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Adapter.EmergencyAdapter
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.MovieViewModel.EmergencyViewModel
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentEmergencyBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EmergencyFragment : Fragment() { // test main

    private lateinit var binding: FragmentEmergencyBinding
    private var editText: EditText? = null
    private var dataSearch: String = "null"
    var textInputLayOut: TextInputLayout? = null

    @Inject
    lateinit var emergencyAdapter: EmergencyAdapter
    private val viewModel: EmergencyViewModel by viewModels()
    // send data


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        emergencyAdapter.refresh()
        binding = FragmentEmergencyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // click intent
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.stateIntent.collect {
                Log.i("check", "runningProgram: $it")
                when (it) {
                    is EmergencyViewModel.StateControllerIntent.Success -> {
                        Log.i("id", "onCreate: ${it.id}")
                        val bundle = Bundle()
                        bundle.putString("data", it.id.toString())//
                        bundle.putString("checkDataString", "EmergencyFragment")
                        val dataToHome = MainInformationFragment()
                        dataToHome.arguments = bundle
                        fragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, dataToHome)
                            ?.commit()
                    }
                    else -> {}
                }
            }
        }
        // click delete

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.stateDelete.collect {
                when (it) {
                    is EmergencyViewModel.StateControllerDelete.Success -> {
                        var sendDataDelete: DeleteData.Data =
                            DeleteData.Data("1", it.id.toString())
                        val dialog = context?.let { Dialog(it) }
                        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.custom_dialog)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        val btnYes: Button = dialog.findViewById(R.id.btn_yes)
                        val btnNo: Button = dialog.findViewById(R.id.btn_no)
                        var tvMessage: TextView = dialog.findViewById(R.id.custom_message)
                        dialog.show()
                        tvMessage.text = "Are you sure to remove this data"
                        btnYes.setOnClickListener {
                            Log.i("delete", "onCreate: delete")
                            viewModel.deleteDataApi(sendDataDelete)
                            lifecycle.coroutineScope.launchWhenCreated {
                                viewModel.state.collect {
                                    when (it) {
                                        is EmergencyViewModel.StateController.Success -> {
                                            Toast(context).showCustomToast(
                                                "ลบข้อมูลสำเร็จ",
                                                this@EmergencyFragment,
                                                R.color.success
                                            )
                                            val bundle = Bundle()
                                            val dataToEmergency = EmergencyFragment()
                                            dataToEmergency.arguments = bundle
                                            fragmentManager?.beginTransaction()
                                                ?.replace(R.id.fragment_container, dataToEmergency)
                                                ?.commit()
                                        }
                                        else -> {}
                                    }
                                }
                            }
                            dialog.dismiss()
                        }
                        btnNo.setOnClickListener {
                            Log.i("delete", "onCreate: delete error")
                            dialog.dismiss()

                        }
                    }
                    is EmergencyViewModel.StateControllerDelete.Error -> {
                        Toast(context).showCustomToast(
                            "ไม่สามารถลบข้อมูลได้",
                            this@EmergencyFragment,
                            R.color.danger
                        )

                    }
                    else -> {}
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = view.findViewById(R.id.editText)
        textInputLayOut = view.findViewById(R.id.textInputLayOutEmergency)
        var args = this.arguments
        dataSearch = args?.getString("data").toString()

        runningProgram(dataSearch)
//        textInputLayOut!!.setEndIconOnClickListener {
//            viewModel.search = editText!!.text.toString()
//            runningProgram()
//        }
        // it will set data to loop recycleView

    }

    private fun runningProgram(dataSearch: String?) {
        if (dataSearch == "null"){
            viewModel.search = null
        }else{
            viewModel.search = dataSearch
        }
        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.moviesList.collect {
                    emergencyAdapter.submitData(it)
                }
            }
            textInputLayOut!!.setEndIconOnClickListener {
                Log.i("reload", "runningProgram: reload")
                val bundle = Bundle()
                bundle.putString("data", editText.text.toString())//
                bundle.putString("checkDataString", "EmergencyFragment")
                val dataToHome = EmergencyFragment()
                dataToHome.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, dataToHome)
                    ?.commit()
            }
            //click to intent
            emergencyAdapter.setOnItemClickListener {
                val data = it.id
                Log.i("test01", "runningProgram: $data")
                viewModel.getDataCheckIntent(data!!)
            }

            // click to delete
            emergencyAdapter.setOnItemClickListenerDeleteEmergency {
                var dataDelete = it
                var data = it.id
                viewModel.getDataCheckDelete(data!!)

//                btnYes.setOnClickListener {
//                    viewModel.getMovieDetail(dataDelete.id!!) // check data list emergency have not null
//                    lifecycle.coroutineScope.launchWhenCreated {
//                        viewModel.state.collect {
//                            when (it) {
//                                is EmergencyViewModel.StateController.Success -> {
//                                    Log.i("sDel", "runningProgram:success ")
//                                    var sendDataDelete: DeleteData.Data =
//                                        DeleteData.Data("1", dataDelete.id.toString())
//                                    viewModel.deleteDataApi(sendDataDelete)
//                                    lifecycle.coroutineScope.launchWhenCreated {
//                                        viewModel.state.collect {
//                                            when (it) {
//                                                is EmergencyViewModel.StateController.Success -> {
//                                                    Log.i("del1", "runningProgram: success")
//
//                                                    Toast(context).showCustomToast(
//                                                        "ลบข้อมูลสำเร็จ",
//                                                        this@EmergencyFragment,
//                                                        R.color.success
//                                                    )
//                                                    val bundle = Bundle()
//                                                    val dataToEmergency = EmergencyFragment()
//                                                    dataToEmergency.arguments = bundle
//                                                    fragmentManager?.beginTransaction()
//                                                        ?.replace(R.id.fragment_container, dataToEmergency)?.commit()
//                                                }
//                                                is EmergencyViewModel.StateController.Error -> {
//                                                    Log.i("del1", "runningProgram: error")
//
//                                                    Toast(context).showCustomToast(
//                                                        "ลบข้อมูลไม่สำเร็จ",
//                                                        this@EmergencyFragment,
//                                                        R.color.danger
//                                                    )
//                                                }
//                                                else -> {}
//                                            }
//                                        }
//                                    }
//                                }
//                                is EmergencyViewModel.StateController.Error ->{
//                                    Log.i("del1", "runningProgram: error")
//                                    Toast(context).showCustomToast(
//                                        "ลบข้อมูลไม่สำเร็จ",
//                                        this@EmergencyFragment,
//                                        R.color.danger
//                                    )
//                                }
//                                else -> {}
//                            }
//                        }
//                    }
//                    dialog.dismiss()
//
//                }
//                btnNo.setOnClickListener {
//                    dialog.dismiss()
//                }


            }

            // refresh when slide end
            lifecycleScope.launchWhenCreated {
                emergencyAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }
            //adapter loop date
            rlMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = emergencyAdapter
            }

            // click to retry when connection fail
            rlMovies.adapter = emergencyAdapter.withLoadStateFooter(
                LoadMoreAdapter {
                    emergencyAdapter.retry()
                }
            )

        }
    }

}