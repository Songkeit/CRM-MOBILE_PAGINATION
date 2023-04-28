package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.hilt_retrofit_paging_youtube.Component.showCustomToast
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Adapter.LoadMoreAdapter
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Adapter.NormalAdapter
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentNormalBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.NormalViewModel.NormalViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NormalFragment : Fragment() {

    private lateinit var binding: FragmentNormalBinding
    private var editText: EditText? = null
    var textInputLayOut: TextInputLayout? = null
    var btnMode: Button? = null




    //var btnCheck: Boolean = false
    private var dataSearch: String = "null"



    @Inject
    lateinit var normalAdapter: NormalAdapter
    private val viewModel: NormalViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNormalBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //click to intent
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.stateIntent.collect {
                when (it) {
                    is NormalViewModel.StateControllerIntent.Success -> {
                        val bundle = Bundle()
                        bundle.putString("data", it.id.toString())//
                        bundle.putString("checkDataString", "NormalFragment")
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
                    is NormalViewModel.StateControllerDelete.Success -> {
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
                                        is NormalViewModel.StateController.Success -> {
                                            Toast(context).showCustomToast(
                                                "ลบข้อมูลสำเร็จ",
                                                this@NormalFragment,
                                                R.color.success
                                            )
                                            val bundle = Bundle()
                                            val dataToEmergency = NormalFragment()
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
                    else -> {}
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = view.findViewById(R.id.editTextNormal)
        textInputLayOut = view.findViewById(R.id.textInputLayOutNormal)
        btnMode = view.findViewById(R.id.btn_mode_delete)
        var args = this.arguments
        dataSearch = args?.getString("data").toString()
        runningProgram(dataSearch)

//        if (stateDel){
//            viewModelStateDelete.setDelete(true)
//        }else{
//            viewModelStateDelete.setDelete(false)
//        }

//        btnMode!!.setOnClickListener{
//            btnCheck = !(btnCheck)
//            viewModel.stateClickDelete = btnCheck
//
//        }
        //  viewModel.stateClickDelete = btnCheck


    }

    private fun runningProgram(dataSearch: String) {

        if (dataSearch == "null") {
            viewModel.search = null
        } else {
            viewModel.search = dataSearch
        }
        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.normalList.collect {
                    normalAdapter.submitData(it)
                }
            }
            // search
            textInputLayOut!!.setEndIconOnClickListener {
                val bundle = Bundle()
                bundle.putString("data", editText!!.text.toString())//
                bundle.putString("checkDataString", "NormalFragment")
                val dataToHome = NormalFragment()
                dataToHome.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, dataToHome)
                    ?.commit()
            }

//            btnModeDelete.setOnClickListener {
//                s = !(s)
//               if (s){
//                   viewModelStateDelete.setDelete(true)
//               }
////                if (stateDel!!){
////                    ValueStateDel().stateDelBoo = true
////                    Log.i("show", "runningProgram: true ${ValueStateDel().stateDelBoo}")
////                }else{
////                    ValueStateDel().stateDelBoo = false
////
////                    Log.i("show", "runningProgram: false ${ValueStateDel().stateDelBoo}")
////                }
////                var count = 0
////                count +=1
//                //ValueStateDel().stateDelBoo = count % 2 == 0
// //               ValueStateDel().stateDelBoo = !(ValueStateDel().stateDelBoo)!!
////                if (stateDel.stateDelBoo == true || stateDel.stateDelBoo == null) {
//////                    value.stateDel = "1"
////                    //preferManager.saveStateDelete("1")
//                    val bundle = Bundle()
//                    bundle.putString("data", editText!!.text.toString())//
//                    bundle.putString("checkDataString", "NormalFragment")
//                    val dataToHome = NormalFragment()
//                    dataToHome.arguments = bundle
//                    fragmentManager?.beginTransaction()
//                        ?.replace(R.id.fragment_container, dataToHome)
//                        ?.commit()
////                } else {
////                    //value.stateDel = "0"
////                   // preferManager.saveStateDelete("0")
////                    val bundle = Bundle()
////                    bundle.putString("data", editText!!.text.toString())//
////                    bundle.putString("checkDataString", "NormalFragment")
////                    val dataToHome = NormalFragment()
////                    dataToHome.arguments = bundle
////                    fragmentManager?.beginTransaction()
////                        ?.replace(R.id.fragment_container, dataToHome)
////                        ?.commit()
////                }
////                if (stateDel){
////                    viewModel.stateClickDelete = "0"
////                    Log.i("btnDel", "runningProgram: click delete")
////                }else{
////                    viewModel.stateClickDelete = "1"
////                    Log.i("btnDel", "runningProgram: click restore")
////
////                }
//            }


            //click to intent
            normalAdapter.setOnItemClickListener {
                val data = it.id
                Log.i("test01", "runningProgram: $data")
                viewModel.getDataCheckIntent(data!!)
            }

            //delete
            normalAdapter.setOnItemClickListenerDeleteNormal {
                var dataDelete = it.id
                viewModel.getDataCheckDelete(dataDelete!!)
            }

//            textInputLayOut!!.setEndIconOnClickListener {
////                //viewModel.search = editText!!.text.toString()
////                viewModel.search = editText!!.text.toString()
////                runningProgram()
//                binding.apply {
//                    lifecycleScope.launchWhenCreated {
//                        viewModel.normalList.collect {
//                            normalAdapter.submitData(it)
//                        }
//                    }
//                }
//            }

            // refresh when slide end
            lifecycleScope.launchWhenCreated {
                normalAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }
            //adapter loop date
            rlMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = normalAdapter
            }

            // click to retry when connection fail
            rlMovies.adapter = normalAdapter.withLoadStateFooter(
                LoadMoreAdapter {
                    normalAdapter.retry()
                }
            )

        }
    }
}
