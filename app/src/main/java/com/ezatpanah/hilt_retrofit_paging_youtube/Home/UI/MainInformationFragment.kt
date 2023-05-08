package com.ezatpanah.hilt_retrofit_paging_youtube.Home.UI

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.ezatpanah.hilt_retrofit_paging_youtube.Component.SharePreferenceManager
import com.ezatpanah.hilt_retrofit_paging_youtube.Component.showCustomToast
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Model.ApiInformationUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.UI.EmergencyFragment
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.CreateUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.HomeViewModel.MainInformationViewModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.KioskList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.UI.NormalFragment
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentMainInformationBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.Unsaved.UI.UnsavedFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainInformationFragment : Fragment() {

    private val viewModel: MainInformationViewModel by viewModels()

    lateinit var binding: FragmentMainInformationBinding
    private var userId:String = "0"
    private var fromPageName = "empty"
    private var stateDel:Boolean = true
    private var dataIntent:String = "null"
    private var colorBtn:Int = 0
    private var textDel:String = "null"
    private var sendData: ApiInformationUser.Data = ApiInformationUser.Data()
    val TAG = "MovieDetailsFragment"

    private lateinit var hideRadio: LinearLayout
    private lateinit var radio_complete: RadioButton
    private lateinit var radio_incomplete: RadioButton
    private lateinit var radio_noService: RadioButton
    private lateinit var radio_group: RadioGroup
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private lateinit var hide: LinearLayout
    private var autoComplete1: AutoCompleteTextView? = null
    private var autoComplete2: AutoCompleteTextView? = null
    private var autoComplete3: AutoCompleteTextView? = null
    private var autoComplete4: AutoCompleteTextView? = null
    private var date: TextView? = null
    private var time: TextView? = null
    private var prgBarEmergency: ProgressBar? = null
    private lateinit var checkBox: CheckBox
    var spn: AutoCompleteTextView? = null
    private val itemsTypeCalled =
        mutableMapOf("Making Call" to "MAKING", "Receiving Call" to "RECEIVING", "VRI" to "VRI")
    private val itemsTypeFunnel =
        mutableMapOf("Normal" to "NORMAL", "Emergency" to "EMG")

    var resultInformationCreate = CreateUser.Data()

    //spn
    var listDropDown = mutableListOf<String>()
    var listMapOfDropDown = mutableMapOf<String, String>()

    var listServiceTypeDropDown = mutableListOf<String>()
    var listSerivceTypeMapOfDropDown = mutableMapOf<String, String>() // video phone,VP
    var listCallTypeMapOfDropDown = mutableMapOf<String, String>() // kiosk, vrs
    var idCallTypeMapOfDropDown = mutableMapOf<String, String>()
    var listKioskDropDown = mutableListOf<String>()
    var listKioskMapOfDropDown = mutableMapOf<String, String>()
    @Inject
    lateinit var preferManager: SharePreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainInformationBinding.inflate(inflater, container, false)
        activity?.fragmentManager?.popBackStack()
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideRadio = view.findViewById(R.id.hide_radio)
        radio_complete = view.findViewById(R.id.radio_complete)
        radio_incomplete = view.findViewById(R.id.radio_notcomplete)
        radio_noService = view.findViewById(R.id.radio_notservice)
        buttonSave = view.findViewById(R.id.button_save)
        buttonCancel = view.findViewById(R.id.button_cancel)
        radio_group = view.findViewById(R.id.radio_group)
        spn = view.findViewById(R.id.spnId)
        autoComplete1 = view.findViewById(R.id.autoComplete1)
        autoComplete2 = view.findViewById(R.id.autoComplete2)
        autoComplete3 = view.findViewById(R.id.autoComplete3)
        autoComplete4 = view.findViewById(R.id.autoComplete4)
        prgBarEmergency = view.findViewById(R.id.prgBarEmergency)
        hide = view.findViewById(R.id.hidden)
        checkBox = view.findViewById(R.id.checkBox)
        date = view.findViewById(R.id.date_contact)
        time = view.findViewById(R.id.time_contact)
        val formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        date!!.text = LocalDateTime.now().format(formatterDate).toString()
        time!!.text = LocalDateTime.now().format(formatterTime)
        date!!.isEnabled = false
        time!!.isEnabled = false
        var args = this.arguments
        var argsPageName = this.arguments
        var argsDel = this.arguments
        var argsDataIntent = this.arguments
        var argsColorBtn = this.arguments
        var argsTextDel = this.arguments
        userId = args!!.getString("data").toString()

        fromPageName = argsPageName?.getString("checkDataString").toString()
        stateDel = argsDel?.getBoolean("dataDel") == true
        dataIntent = argsDataIntent?.getString("dataIntent").toString()// search
        colorBtn = argsColorBtn?.getInt("colorBtn")!!
        textDel = argsTextDel?.getString("textDel").toString()

        if (userId != "null") {
            viewModel.loadDetailUser(userId!!.toInt()) //โหลดข้อมูลของแต่ละคน
            lifecycle.coroutineScope.launchWhenCreated {
                viewModel.stateDetail.collect {
                    when (it) {
                        is MainInformationViewModel.StateController.Success -> {
                            var argsData = it.data
                            sendData = argsData!!
                            if (argsData!!.insertStatus == 2 && fromPageName =="UnsavedFragment") {
                                sendData.insertStatus = 1
                            }
                            Log.i("swndData", "onViewCreated: ${it.data}")
                            binding.apply {
                                contactFirstname.setText(argsData!!.srcFirstName)
                                contactSurname.setText(argsData.srcLastName)
                                contactNumber.setText(argsData.src)
                                destinationContactFirstname.setText(argsData.dstFirstName)
                                destinationContactSurname.setText(argsData.dstLastName)
                                destinationContactNumber.setText(argsData.dst)
                                Log.i("sen", "onViewCreated: $sendData")
                                selectRadioButton(argsData)
                                displayKioakDropDown()
                                sendData.datebegin?.let {
                                    var splitDateTime = sendData.datebegin!!.split(" ")
                                    val intentDate = splitDateTime[0]
                                    val intentTime = splitDateTime[1].split(":")
                                    date!!.text = intentDate
                                    time!!.text = "${intentTime[0]}:${intentTime[1]}"
                                    date!!.isEnabled = false
                                    time!!.isEnabled = false
                                }
                                chatLog.setText(argsData.message)
                                discussionSummary.setText(argsData.summaryAgent)
                                checkBox.isChecked = sendData.isEmergency!!.toInt() == 1
                                checkBox.setOnClickListener {
                                    if (checkBox.isChecked) {
                                        sendData.isEmergency = 1
                                    } else {
                                        sendData.isEmergency = 0
                                    }
                                }
                                buttonSave.setOnClickListener {
                                    sendData.srcFirstName = contactFirstname.text.toString()
                                    sendData.srcLastName = contactSurname.text.toString()
                                    sendData.src = contactNumber.text.toString()
                                    sendData.dstFirstName =
                                        destinationContactFirstname.text.toString()
                                    sendData.dstLastName = destinationContactSurname.text.toString()
                                    sendData.dst = destinationContactNumber.text.toString()
                                    sendData.message = chatLog.text.toString()
                                    sendData.summaryAgent = discussionSummary.text.toString()
                                    Log.i("final sendata", "onViewCreated: ${sendData}")
                                    updateData()
                                    Log.i("final sendata1", "onViewCreated: update")

                                }

                            }

                        }
                        else -> {}
                    }
                }
            }
        }
        binding.apply {
            selectRadioButton(null)
            displayKioakDropDown()
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    resultInformationCreate.isEmergency = "1"
                } else {
                    resultInformationCreate.isEmergency = "0"
                }
            }

            buttonSave.setOnClickListener {
                resultInformationCreate.agentimport = "agenttest01"
                resultInformationCreate.srcFirstName = contactFirstname.text.toString()
                resultInformationCreate.srcLastName = contactSurname.text.toString()
                resultInformationCreate.src = contactNumber.text.toString()
                resultInformationCreate.dstFirstName =
                    destinationContactFirstname.text.toString()
                resultInformationCreate.dstLastName = destinationContactSurname.text.toString()
                resultInformationCreate.dst = destinationContactNumber.text.toString()
                resultInformationCreate.message = chatLog.text.toString()
                resultInformationCreate.summaryAgent = discussionSummary.text.toString()
                Log.i("final sendata", "onViewCreated: create")
                Log.i("re", "onViewCreated: $resultInformationCreate")
                createData()
            }
            buttonCancel.setOnClickListener {
                cancelCreateButton()
            }

        }


    }

    private fun cancelCreateButton() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("ยกเลิกการบันทึกข้อมูล")
        builder.setMessage("คุณแน่ใจว่าจะยกเลิกการันทึก")
        builder.setPositiveButton("OK") { dialog, which ->
            val bundle = Bundle()
            if (fromPageName == "NormalFragment") {
                val dataToNormal = NormalFragment()
                bundle.putBoolean("dataDel", stateDel)//
                dataToNormal.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, dataToNormal)?.commit()
            } else if (fromPageName == "EmergencyFragment") {
                val dataToEmergency = EmergencyFragment()
                dataToEmergency.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, dataToEmergency)?.commit()
            } else if (fromPageName == "UnsavedFragment") {
                val dataToUnsaed = UnsavedFragment()
                dataToUnsaed.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, dataToUnsaed)?.commit()
            } else {
                val dataToMainInformation = MainInformationFragment()
                dataToMainInformation.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, dataToMainInformation)?.commit()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // Do something when the "Cancel" button is clicked
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun createData() {
        if (resultInformationCreate.messageTypeId == null){
            Log.i(TAG, "createData: create error")
        }else{
            viewModel.createData(resultInformationCreate)
            lifecycle.coroutineScope.launchWhenCreated {
                viewModel.stateCreate.collect {
                    when (it) {
                        is MainInformationViewModel.StateControllerCreate.Success -> {
                            val bundle = Bundle()
                            val dataToHome = HomeFragment()
                            dataToHome.arguments = bundle
                            fragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, dataToHome)?.commit()
                            Toast(context).showCustomToast(
                                it.data,
                                this@MainInformationFragment,
                                R.color.success
                            )
                        }
                        is MainInformationViewModel.StateControllerCreate.Error -> {
                            Toast(context).showCustomToast(
                                it.message,
                                this@MainInformationFragment,
                                R.color.danger
                            )
                        }
                        else -> {}
                    }
                }
            }
        }

    }

    private fun updateData() {
        if (sendData.messageTypeId == null) {
            Log.i("error", "updateData: เลือกประเภทเรื่อง")
        } else {
            viewModel.updateData(sendData)
            Log.i(TAG, "updateData: unsaved1")
            lifecycle.coroutineScope.launchWhenCreated {
                viewModel.stateUpdate.collect {
                    when (it) {
                        is MainInformationViewModel.StateControllerUpdate.Success -> {
                            Toast(context).showCustomToast(
                                it.data, this@MainInformationFragment,
                                R.color.success
                            )
                            prgBarEmergency!!.visibility = View.GONE
                            val bundle = Bundle()
                            if (fromPageName == "NormalFragment") {
                                val dataToNormal = NormalFragment()
                                bundle.putBoolean("dataDel", !stateDel)//
                                bundle.putString("dataIntent", dataIntent)//
                                bundle.putInt("colorBtn", colorBtn)
                                if (!stateDel){
                                    colorBtn = Color.RED
                                    textDel = "ข้อมูลที่ถูกลบ"
                                }else{
                                    colorBtn = Color.BLUE
                                    textDel = "รายการข้อมูล"
                                }
                                bundle.putString("textDel", textDel)//
                                dataToNormal.arguments = bundle
                                fragmentManager?.beginTransaction()
                                    ?.replace(R.id.fragment_container, dataToNormal)?.commit()
                            } else if (fromPageName == "EmergencyFragment") {
                                val dataToEmergency = EmergencyFragment()
                                dataToEmergency.arguments = bundle
                                fragmentManager?.beginTransaction()
                                    ?.replace(R.id.fragment_container, dataToEmergency)?.commit()
                            } else {
                                val dataToUnsave = UnsavedFragment()
                                dataToUnsave.arguments = bundle
                                fragmentManager?.beginTransaction()
                                    ?.replace(R.id.fragment_container, dataToUnsave)?.commit()
                            }
                        }
                        is MainInformationViewModel.StateControllerUpdate.Error -> {
                            Toast(context).showCustomToast(
                                it.message,
                                this@MainInformationFragment,
                                R.color.danger
                            )
                        }
                        else -> {}
                    }
                }
            }
        }


    }

    private fun displayKioakDropDown() {
        getDataServiceTypeDropDown()

        var listTypeCalledAutoComplate3 = mutableListOf<String>()
        for (i in itemsTypeCalled.keys) {
            listTypeCalledAutoComplate3.add(i)
        }
        val adapterCalled: ArrayAdapter<String>? =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.list_items,
                    listTypeCalledAutoComplate3
                )
            }
        sendData?.calltype.let {
            for ((k, v) in itemsTypeCalled.entries) {
                if (v == it)
                    autoComplete3?.setText(k)
                Log.i("t", "displayKioakDropDown: $it , $v")
            }
        }


        autoComplete3?.setAdapter(adapterCalled)
        autoComplete3?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val itemSelected = adapterCalled!!.getItem(position)
                sendData.calltype = itemsTypeCalled[itemSelected]
                resultInformationCreate.calltype = itemsTypeCalled[itemSelected]
            }


        var listFunalAutoComplate4 = mutableListOf<String>()
        for (i in itemsTypeFunnel.keys) {
            listFunalAutoComplate4.add(i)
        }
        val adapterFunnel: ArrayAdapter<String>? =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.list_items,
                    listFunalAutoComplate4
                )
            }
        sendData?.logtype.let {
            for ((k, v) in itemsTypeFunnel.entries) {
                if (v == it)
                    autoComplete4?.setText(k)
            }
        }
        autoComplete4?.setAdapter(adapterFunnel)
        autoComplete4?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val itemSelected = adapterFunnel!!.getItem(position)
                Toast.makeText(context, itemSelected.toString(), Toast.LENGTH_SHORT).show()
                sendData.logtype = itemsTypeFunnel[itemSelected]
                resultInformationCreate.logtype = itemsTypeFunnel[itemSelected]
            }
    }

    private fun getDataServiceTypeDropDown() {
        viewModel.serviceTypeDropDown()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.stateservicelist.collect {
                when (it) {
                    is MainInformationViewModel.StateControllerServiceList.Loading -> {
                        prgBarEmergency!!.visibility = View.GONE
                    }
                    is MainInformationViewModel.StateControllerServiceList.Success -> {

                        prgBarEmergency!!.visibility = View.INVISIBLE
                        preferManager.savePreferenceServiceList(it.dataList)
                        appendServiceTypeInListDropDown(it.dataList!!)
                        dropDownServiceTypeList()
                    }
                    is MainInformationViewModel.StateControllerServiceList.Error -> {
                        Log.i("dataLIst", "getDataServiceTypeDropDown: ${it.message}")
                    }
                    else -> {}
                }
            }
        }
    }

    private fun dropDownServiceTypeList() {
        var adapterServiceType: ArrayAdapter<String>? =
            context?.let { ArrayAdapter(it, R.layout.list_items, listServiceTypeDropDown) }

        sendData.servicetype?.let {
            for ((k, v) in listSerivceTypeMapOfDropDown.entries) {
                if (v == it) {
                    autoComplete1?.setText(k)
                    Log.i("ay", "dropDownServiceTypeList:${listCallTypeMapOfDropDown} ")
                }
            }
        }
        autoComplete1?.setAdapter(adapterServiceType)
        if (sendData.srctype?.toInt() == 3) {
            hide.visibility = View.VISIBLE
            getDataKioskDropDown()
        } else {
            hide.visibility = View.GONE
        }

        autoComplete1?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                var itemSelect = adapterServiceType!!.getItem(position).toString() // เลือกdropdown
                sendData.servicetype =
                    listSerivceTypeMapOfDropDown[itemSelect]?.uppercase()
                resultInformationCreate.servicetype = listSerivceTypeMapOfDropDown[itemSelect]
                Log.i("tag", "dropDownServiceTypeList: ${itemSelect!!.uppercase()}")

                //เพิ่ม ID
                sendData?.kioskId = listCallTypeMapOfDropDown[itemSelect]
                resultInformationCreate.service_name = listCallTypeMapOfDropDown[itemSelect]

                //ตัวเลขของการเลือก
                sendData?.srctype = idCallTypeMapOfDropDown[itemSelect]!!.toInt()
                resultInformationCreate.srctype = idCallTypeMapOfDropDown[itemSelect]
                if (idCallTypeMapOfDropDown[itemSelect] == "3" ||
                    sendData?.srctype!!.toInt() == 3
                ) {
                    hide.visibility = View.VISIBLE

                    // display when data from select
                    getDataKioskDropDown()
                } else {
                    hide.visibility = View.GONE
                }
            }
    }

    private fun getDataKioskDropDown() {
        viewModel.kioskDropDown()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.statekiosk.collect {
                when (it) {
                    is MainInformationViewModel.StateControllerKiosk.Loading -> {
                        prgBarEmergency!!.visibility = View.GONE

                    }
                    is MainInformationViewModel.StateControllerKiosk.Success -> {
                        prgBarEmergency!!.visibility = View.INVISIBLE

                        appendKioskInListDropDown(it.dataList!!)
                        Log.i(TAG, "getDataKioskDropDown:$listKioskDropDown ")
                        dropDownKioskList()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun dropDownKioskList() {
        var adapterKiosk: ArrayAdapter<String>? =
            context?.let { ArrayAdapter(it, R.layout.list_items, listKioskDropDown) }

        for ((key, value) in listKioskMapOfDropDown.entries) {
            if (value == sendData?.kioskId) {
                sendData?.kioskId.let {
                    autoComplete2?.setText(key)
                }
            }

        }
        autoComplete2?.setAdapter(adapterKiosk)
        autoComplete2?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val itemSelect = adapterKiosk!!.getItem(position)
                val itemSelectId = listKioskMapOfDropDown[itemSelect]
                sendData.kioskId = itemSelectId
                resultInformationCreate.kioskId = itemSelectId
                Toast.makeText(context, itemSelectId.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun appendKioskInListDropDown(data: KioskList) {
        listKioskDropDown.clear()
        listKioskMapOfDropDown.clear()
        val kioskMaxSize = data.data.size
        for (i in 0 until kioskMaxSize) {
            var kioskText = data.data[i].name.toString()
            var kioskId = data.data[i].kioskId.toString()
            listKioskDropDown.add(kioskText)
            listKioskMapOfDropDown[kioskText] = kioskId // ["ชื่อ","KIOSK123"]
        }
    }

    private fun appendServiceTypeInListDropDown(data: ServiceList) {
        listServiceTypeDropDown.clear()
        listSerivceTypeMapOfDropDown.clear()
        val serviceMaxSize = data.data.size
        for (i in 0 until serviceMaxSize) {
            listServiceTypeDropDown.add(data.data[i].name.toString())
            listSerivceTypeMapOfDropDown[data.data[i].name.toString()] =
                data.data[i].asteriskKeyName.toString()
            listCallTypeMapOfDropDown[data.data[i].name.toString()] =
                data.data[i].serviceTypeName.toString()
            idCallTypeMapOfDropDown[data.data[i].name.toString()] = data.data[i].id.toString()
        }
    }

    private fun selectRadioButton(response: ApiInformationUser.Data?) {
        var SERVICE: Int? = null
        response?.messageTypeStatus?.let {
            checkDefaultRadio(response) // set default
            hideRadio.visibility = View.VISIBLE
        }
        radio_group.setOnCheckedChangeListener { _, checkId ->
            if (checkId == R.id.radio_complete) {
                SERVICE = 1
            }
            if (checkId == R.id.radio_notcomplete) {
                SERVICE = 0
            }
            if (checkId == R.id.radio_notservice) {
                SERVICE = 2
            }
            hideRadio.visibility = View.VISIBLE
            sendData.messageTypeStatus = SERVICE!!.toInt()
            resultInformationCreate.messageTypeStatus = SERVICE.toString()
            getDataDropDown(SERVICE!!, null)
        }
    }

    private fun checkDefaultRadio(response: ApiInformationUser.Data) {
        var SERVICE: Int? = null
        when (response.messageTypeStatus) {
            1 -> {
                radio_complete.isChecked = true
                SERVICE = 1
            }
            0 -> {
                radio_incomplete.isChecked = true
                SERVICE = 0
            }
            2 -> {
                radio_noService.isChecked = true
                SERVICE = 2
            }
        }
        sendData.messageTypeStatus = SERVICE!!.toInt()
        getDataDropDown(SERVICE!!, response.messageTypeId.toString())

    }

    fun getDataDropDown(service: Int, message_id: String?) {//  service and service id
        viewModel.typeStory()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.statetypeStory.collect {
                when (it) {
                    is MainInformationViewModel.StateControllerTypeStory.Success -> {
                        Log.i("song", "getDataDropDown: ${it.dataList}")
                        preferManager.savePreferenceTypeStory(it.dataList)
                        forLopingListDropDown(it.dataList!!, service)// list ทั้งหมดของ service
                        if (message_id != null) { // only detail (message type id not null)
                            sendData.messageTypeId = message_id.toInt()
                            dropDownList(message_id.toInt())
                        } else {
                            dropDownList(null)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun dropDownList(messageTypeId: Int?) {
        Log.i("test9874561231", "dropDownList: $listMapOfDropDown")
//        val adapterSpinner: ArrayAdapter<String>? =
//            context?.let {
//                ArrayAdapter(
//                    it,
//                    R.layout.my_select_item_limit,
//                    listDropDown
//                )
//            }
        var adapterSpinner: ArrayAdapter<String>? =
            context?.let { ArrayAdapter(it, R.layout.list_items, listDropDown) }//list name

        for ((key, value) in listMapOfDropDown.entries) {
            if (value == messageTypeId.toString()) {
                spn!!.setText(key)
            }
        }
        spn!!.setAdapter(adapterSpinner)
        adapterSpinner?.setDropDownViewResource(R.layout.my_dropdown_item_limit)
        spn!!.setAdapter(adapterSpinner)
        spn!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val itemSelect = adapterSpinner!!.getItem(position)
            val itemSelectId = listMapOfDropDown[itemSelect]
            sendData.messageTypeId = listMapOfDropDown[itemSelect]!!.toInt()
            resultInformationCreate.messageTypeId = listMapOfDropDown[itemSelect]

        }
    }


    private fun forLopingListDropDown(results: TypeStoryModel, service: Int) {//CompleteType(typeId=6, typeText=TTRS-การแพทย์ฉุกเฉิน, order=2, isEmergency=1, isTest=0)
        listDropDown.clear()
        listMapOfDropDown.clear()
        when (service) {
            0 -> {
                spn!!.text = null
                sendData.messageTypeId = null
                resultInformationCreate.messageTypeId = null
                listDropDown.clear()
                listMapOfDropDown.clear()
                val typeTextMaxSize = results.data!!.incompleteType.size
                for (i in 0 until typeTextMaxSize) {
                    val typeText = results.data!!.incompleteType[i].typeText.toString()
                    val messageTypeId = results.data!!.incompleteType[i].typeId.toString()
                    listDropDown.add(typeText)
                    listMapOfDropDown[typeText] = messageTypeId
                }
            }
            1 -> {
                spn!!.text = null
                sendData.messageTypeId = null
                resultInformationCreate.messageTypeId = null
                listDropDown.clear()
                listMapOfDropDown.clear()
                val typeTextMaxSize = results.data!!.completeType.size
                for (i in 0 until typeTextMaxSize) {
                    val typeText = results.data!!.completeType[i].typeText.toString()
                    val messageTypeId = results.data!!.completeType[i].typeId.toString()
                    listDropDown.add(typeText)
                    listMapOfDropDown[typeText] = messageTypeId
                }
            }
            2 -> {
                spn!!.text = null
                sendData.messageTypeId = null
                resultInformationCreate.messageTypeId = null
                listDropDown.clear()
                listMapOfDropDown.clear()
                val typeTextMaxSize = results.data!!.noserviceType.size
                for (i in 0 until typeTextMaxSize) {
                    val typeText = results.data!!.noserviceType[i].typeText.toString()
                    val messageTypeId = results.data!!.noserviceType[i].typeId.toString()
                    listDropDown.add(typeText)
                    listMapOfDropDown[typeText] = messageTypeId

                }
            }
        }
    }
}