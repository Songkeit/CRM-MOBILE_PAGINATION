package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.ezatpanah.hilt_retrofit_paging_youtube.Component.showCustomToast
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.KioskList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Model.ApiInformationUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.NormalViewModel.NormalViewModel
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentNormalDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NormalDetailFragment : Fragment() {

    private lateinit var binding: FragmentNormalDetailBinding
    private var normalId = 0
    private val args: NormalDetailFragmentArgs by navArgs()
    private val viewModel: NormalViewModel by viewModels()
    private lateinit var hideRadio: LinearLayout
    private lateinit var radio_complete: RadioButton
    private lateinit var radio_incomplete: RadioButton
    private lateinit var radio_noService: RadioButton
    private lateinit var radio_group: RadioGroup
    private lateinit var buttonSave: Button
    private lateinit var hide: LinearLayout
    private var autoComplete1: AutoCompleteTextView? = null
    private var autoComplete2: AutoCompleteTextView? = null
    private var autoComplete3: AutoCompleteTextView? = null
    private var autoComplete4: AutoCompleteTextView? = null
    private var date: TextView? = null
    private var time: TextView? = null
    private var prgBarNormals: ProgressBar? = null
    private lateinit var checkBox: CheckBox

    var spn: AppCompatSpinner? = null
    private val itemsTypeCalled =
        mutableMapOf("Making Call" to "MAKING", "Receiving Call" to "RECEIVING", "VRI" to "VRI")
    private val itemsTypeFunnel =
        mutableMapOf("Normal" to "NORMAL", "Emergency" to "EMG")
    private var sendData: ApiInformationUser.Data = ApiInformationUser.Data()


    var listDropDown = mutableListOf<String>()
    var listMapOfDropDown = mutableMapOf<String, String>()
    var listServiceTypeDropDown = mutableListOf<String>()
    var listSerivceTypeMapOfDropDown = mutableMapOf<String, String>() // video phone,VP
    var listCallTypeMapOfDropDown = mutableMapOf<String, String>() // kiosk, vrs
    var idCallTypeMapOfDropDown = mutableMapOf<String, String>()
    var listKioskDropDown = mutableListOf<String>()
    var listKioskMapOfDropDown = mutableMapOf<String, String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel.detailsNormal.observe(viewLifecycleOwner) { response ->
            sendData = response!!

        }
        binding = FragmentNormalDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        normalId = args.normalId
        if (normalId > 0) {
            viewModel.loadDetailsNormal(normalId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideRadio = view.findViewById(R.id.hide_radio)
        radio_complete = view.findViewById(R.id.radio_complete)
        radio_incomplete = view.findViewById(R.id.radio_notcomplete)
        radio_noService = view.findViewById(R.id.radio_notservice)
        buttonSave = view.findViewById(R.id.button_save)
        radio_group = view.findViewById(R.id.radio_group)
        spn = view.findViewById(R.id.spnId)
        autoComplete1 = view.findViewById(R.id.autoComplete1)
        autoComplete2 = view.findViewById(R.id.autoComplete2)
        autoComplete3 = view.findViewById(R.id.autoComplete3)
        autoComplete4 = view.findViewById(R.id.autoComplete4)
        hide = view.findViewById(R.id.hidden)
        checkBox = view.findViewById(R.id.checkBox)
        date = view.findViewById(R.id.date_contact)
        time = view.findViewById(R.id.time_contact)
        prgBarNormals = view.findViewById(R.id.prgBarNormals)



        binding.apply {

            viewModel.detailsNormal.observe(viewLifecycleOwner) { response ->
                Log.i("sendData", "onViewCreated: ${sendData}")
                contactFirstname.setText(response!!.srcFirstName)
                contactSurname.setText(response.srcLastName)
                contactNumber.setText(response.src)
                destinationContactFirstname.setText(response.dstFirstName)
                destinationContactSurname.setText(response.dstLastName)
                destinationContactNumber.setText(response.dst)
                selectRadioButton(response)
                displayKioakDropDown()
                var splitDateTime = sendData.datebegin!!.split(" ")
                val intentDate = splitDateTime[0]
                val intentTime = splitDateTime[1].split(":")
                date!!.text = intentDate
                time!!.text = "${intentTime[0]}:${intentTime[1]}"
                date!!.isEnabled = false
                time!!.isEnabled = false
                chatLog.setText(response.message)
                discussionSummary.setText(response.summaryAgent)
                checkBox.isChecked = sendData.isEmergency == 1
                checkBox.setOnClickListener {
                    if (checkBox.isChecked) {
                        sendData.isEmergency = 1
                    } else {
                        sendData.isEmergency = 0
                    }
                }
            }

            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    prgBarNormals.visibility = View.VISIBLE
                } else {
                    prgBarNormals.visibility = View.INVISIBLE
                }
            }
            buttonSave.setOnClickListener {
                sendData.srcFirstName = contactFirstname.text.toString()
                sendData.srcLastName = contactSurname.text.toString()
                sendData.src = contactNumber.text.toString()
                sendData.dstFirstName = destinationContactFirstname.text.toString()
                sendData.dstLastName = destinationContactSurname.text.toString()
                sendData.dst = destinationContactNumber.text.toString()
                sendData.message = chatLog.text.toString()
                sendData.summaryAgent = discussionSummary.text.toString()
                Log.i("final sendata", "onViewCreated: ${sendData}")
                updateNormal()
            }
        }
    }

    private fun updateNormal() {
        viewModel.updateData(sendData)
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.stateUpdate.collect {
                when (it) {
                    is NormalViewModel.StateControllerUpdate.Success -> {
                        Toast(context).showCustomToast(
                            it.data, this@NormalDetailFragment,
                            R.color.success
                        )
                        viewModel.loading.observe(viewLifecycleOwner) {
                            if (it) {
                                prgBarNormals!!.visibility = View.GONE
                                val bundle = Bundle()
                                val dataToNormal = NormalFragment()
                                dataToNormal.arguments = bundle
                                fragmentManager?.beginTransaction()
                                    ?.replace(R.id.fragment_container, dataToNormal)?.commit()
                            } else {
                                prgBarNormals!!.visibility = View.INVISIBLE
                            }
                        }

                    }
                    is NormalViewModel.StateControllerUpdate.Error -> {
                        Toast(context).showCustomToast(
                            it.message,
                            this@NormalDetailFragment,
                            R.color.danger
                        )
                    }
                    else -> {}
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
            }
    }

    private fun getDataServiceTypeDropDown() { //ช่องทางการติดต่อ
        Log.i("dataLIst", "getDataServiceTypeDropDown: part1")

        viewModel.serviceTypeDropDown()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.stateservicelist.collect {
                when (it) {
                    is NormalViewModel.StateControllerServiceList.Success -> {
                        viewModel.loading.observe(viewLifecycleOwner) {
                            if (it) {
                                prgBarNormals!!.visibility = View.GONE
                            } else {
                                prgBarNormals!!.visibility = View.INVISIBLE
                            }
                        }
                        appendServiceTypeInListDropDown(it.dataList!!)
                        dropDownServiceTypeList()
                    }
                    is NormalViewModel.StateControllerServiceList.Error -> {
                        Log.i("dataLIst", "getDataServiceTypeDropDown: ${it.message}")
                    }
                    else -> {}
                }
            }
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

    @SuppressLint("SetTextI18n")
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
        if (sendData?.srctype == 3) {
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
                Log.i("tag", "dropDownServiceTypeList: ${itemSelect!!.uppercase()}")

                //เพิ่ม ID
                sendData?.kioskId = listCallTypeMapOfDropDown[itemSelect]

                //ตัวเลขของการเลือก
                sendData?.srctype = idCallTypeMapOfDropDown[itemSelect]!!.toInt()
                if (idCallTypeMapOfDropDown[itemSelect] == "3" ||
                    sendData?.srctype == 3
                ) {
                    hide.visibility = View.VISIBLE

                    // display when data from select
                    getDataKioskDropDown()
                } else {
                    hide.visibility = View.GONE
                }
            }
    }

    private fun getDataKioskDropDown() { // when user select kiosk id 3
        viewModel.kioskDropDown()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.statekiosk.collect {
                when (it) {
                    is NormalViewModel.StateControllerKiosk.Success -> {
                        viewModel.loading.observe(viewLifecycleOwner) {
                            if (it) {
                                prgBarNormals!!.visibility = View.GONE
                            } else {
                                prgBarNormals!!.visibility = View.INVISIBLE
                            }
                        }
                        appendKioskInListDropDown(it.dataList!!)
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
                sendData?.kioskId = itemSelectId
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

    private fun selectRadioButton(response: ApiInformationUser.Data) {
        var SERVICE: Int? = null
        response.messageTypeStatus?.let {
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
            sendData.messageTypeStatus = SERVICE
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
        sendData.messageTypeStatus = SERVICE
        getDataDropDown(SERVICE!!, response.messageTypeId!!)

    }

    fun getDataDropDown(service: Int, message_id: Int?) {// call service
        viewModel.typeStory()
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.statetypeStory.collect {
                when (it) {
                    is NormalViewModel.StateControllerTypeStory.Success -> {
                        forLopingListDropDown(it.dataList!!, service)
                        if (message_id != null) { // only detail (message type id not null)
                            dropDownList(message_id)
                        } else {
                            dropDownList(null)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun dropDownList(messageTypeId: Int?) { // ตัวกด list drop down
        Log.i("test9874561231", "dropDownList: $listMapOfDropDown")
        val adapterSpinner: ArrayAdapter<String>? =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.list_items,
                    listDropDown
                )
            }
        spn!!.adapter = adapterSpinner
        if (messageTypeId != null) {
            var positionMapListDropDown = findPositionOfMapListOfDropDown(messageTypeId!!)
            spn!!.setSelection(positionMapListDropDown)
            Log.i("b5", "dropDownList: $messageTypeId ")
        }

        spn!!.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var item: String = listDropDown[position]
                if (position != 0) {
                    sendData.messageTypeId = listMapOfDropDown[item]!!.toInt()
                } else {
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun findPositionOfMapListOfDropDown(messageTypeId: Int): Int {
        var index: Int? = null
        listMapOfDropDown.forEach { item ->
            if (messageTypeId.toString() == item.value) {
                index = listDropDown.indexOf(item.key)
            }
        }
        return index!!
    }

    private fun forLopingListDropDown(results: TypeStoryModel, service: Int) {
        val TAG = "Display"
        spn!!.setSelection(0)
        when (service) {
            0 -> {
                listDropDown.clear()
                listMapOfDropDown.clear()
                listDropDown.add("Select")
                val typeTextMaxSize = results.data!!.incompleteType.size
                for (i in 0 until typeTextMaxSize) {
                    val typeText = results.data!!.incompleteType[i].typeText.toString()
                    val messageTypeId = results.data!!.incompleteType[i].typeId.toString()
                    listDropDown.add(typeText)
                    listMapOfDropDown[typeText] = messageTypeId
                }
            }
            1 -> {
                listDropDown.clear()
                listMapOfDropDown.clear()
                listDropDown.add("Select")
                val typeTextMaxSize = results.data!!.completeType.size
                for (i in 0 until typeTextMaxSize) {
                    val typeText = results.data!!.completeType[i].typeText.toString()
                    val messageTypeId = results.data!!.completeType[i].typeId.toString()
                    listDropDown.add(typeText)
                    listMapOfDropDown[typeText] = messageTypeId
                }
            }
            2 -> {
                listDropDown.clear()
                listMapOfDropDown.clear()
                listDropDown.add("Select")
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