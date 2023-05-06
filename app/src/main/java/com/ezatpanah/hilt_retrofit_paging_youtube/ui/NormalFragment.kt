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
    private lateinit var editText: EditText
    var textInputLayOut: TextInputLayout? = null
    var btnMode: Button? = null




    //var btnCheck: Boolean = false
    private var dataSearch: String = "null"
    private var dataDel: Boolean? = false
    private var colorDel: Int? = Color.RED
    private var textDel: String? = "ข้อมูลที่ถูกลบ"


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
                    is NormalViewModel.StateControllerIntent.Error ->{
                        Toast(context).showCustomToast(
                            it.message, this@NormalFragment,
                            R.color.danger
                        )
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
                            if (dataDel!!) DeleteData.Data("0", it.id.toString()) else DeleteData.Data("1", it.id.toString())
                        val dialog = context?.let { Dialog(it) }
                        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.custom_dialog)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        val btnYes: Button = dialog.findViewById(R.id.btn_yes)
                        val btnNo: Button = dialog.findViewById(R.id.btn_no)
                        var tvMessage: TextView = dialog.findViewById(R.id.custom_message)
                        dialog.show()
                        tvMessage.text = if (dataDel!!) "Are you sure to restore this data" else "Are you sure to remove this data"
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
                                            bundle.putBoolean("dataDel", !(dataDel)!!)//
                                            bundle.putInt("colorBtn", colorDel!!)//
                                            bundle.putString("data", editText!!.text.toString())//
                                            bundle.putString("textDel", textDel)//
                                            bundle.putString("checkDataString", "NormalFragment")
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

        val args = this.arguments
        dataSearch = args?.getString("data").toString()

        val argsStateDel = this.arguments
        dataDel = argsStateDel?.getBoolean("dataDel") == dataDel

        val argsStatColor = this.arguments
        colorDel = argsStatColor?.getInt("colorBtn")

        val argsStateText = this.arguments
        textDel = argsStateText?.getString("textDel")

        Log.i("co", "onViewCreated: $colorDel")
        if (colorDel == null || textDel == null){
            btnMode!!.text = "ข้อมูลที่ถูกลบ"
            btnMode!!.setBackgroundColor(Color.RED)
        }else{
            btnMode!!.text = textDel
            Log.i("color", "onViewCreated: $colorDel")
            Log.i("text", "onViewCreated: $textDel")
            colorDel?.let { btnMode!!.setBackgroundColor(it) }
        }

        if (dataSearch.isNullOrEmpty()|| dataSearch =="null"){
            editText.text = null
        }else{
            editText.setText(dataSearch)
        }
        normalAdapter.stateIcon = dataDel


        //btnMode!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_delete_24,0,0,0)

//        if (dataDel!!){
//            btnMode!!.text = "ข้อมูลที่ถูกลบ"
//            btnMode!!.setBackgroundColor(Color.RED)
//            btnMode!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_delete_24,0,0,0)
//        }else{
//            btnMode!!.text = "รายการข้อมูลรับเรื่อง"
//            btnMode!!.setBackgroundColor(Color.CYAN)
//            btnMode!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_arrow_back_ios_new_24,0,0,0)
//        }



        //btnCheck = argsStateDel?.getBoolean("dataDel") == true
        runningProgram(dataSearch,dataDel)


//        if (stateDel){
//            viewModelStateDelete.setDelete(true)
//        }else{
//            viewModelStateDelete.setDelete(false)
//        }



    }

    private fun runningProgram(dataSearch: String?, dataDel: Boolean?) {
        if (dataSearch == "null") {
            viewModel.search = null
        } else {
            viewModel.search = dataSearch
        }
        if (dataDel != null) {
            viewModel.stateDel = dataDel
        }

//        if (btnCheck == "null"){
//            viewModel.stateDel = "0"
//        }else{
//            viewModel.stateDel = "1"
//        }

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
                bundle.putBoolean("dataDel", !(dataDel)!!)//
                if(colorDel == null){
                    bundle.putInt("colorBtn", Color.RED)//
                }else{
                    bundle.putInt("colorBtn", colorDel!!)//
                }
                bundle.putString("textDel", textDel)//
                bundle.putString("checkDataString", "NormalFragment")
                val dataToHome = NormalFragment()
                dataToHome.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, dataToHome)
                    ?.commit()
            }
            btnMode!!.setOnClickListener {
                this@NormalFragment.dataDel = (!dataDel!!)
                if (dataDel){
                    colorDel = Color.RED
                    textDel = "ข้อมูลที่ถูกลบ"
                }else{
                    colorDel = Color.BLUE
                    textDel = "รายการข้อมูล"
                }

                Log.i("789", "runningProgram: $dataDel")
                val bundle = Bundle()
                bundle.putBoolean("dataDel", dataDel)//
                bundle.putInt("colorBtn", colorDel!!)//
                bundle.putString("textDel", textDel)//
                bundle.putString("checkDataString", "NormalFragment")
                val dataToHome = NormalFragment()
                dataToHome.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, dataToHome)
                    ?.commit()

            }

            //click to intent
            normalAdapter.setOnItemClickListener {
                val data = it.id
                viewModel.getDataCheckIntent(data!!)
            }

            //delete & restore
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
