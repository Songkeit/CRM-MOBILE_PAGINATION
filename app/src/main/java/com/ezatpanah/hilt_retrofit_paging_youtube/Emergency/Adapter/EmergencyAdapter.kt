package com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Adapter


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.ItemRecycleViewBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.response.RequestCommonApi
import com.ezatpanah.hilt_retrofit_paging_youtube.Component.SharePreferenceManager
import com.google.gson.Gson
import javax.inject.Inject

class EmergencyAdapter @Inject() constructor(
    private var preferManager: SharePreferenceManager

) :
    PagingDataAdapter<RequestCommonApi.Data, EmergencyAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemRecycleViewBinding
    private lateinit var context: Context

    var typeStory = preferManager.getPreferenceTypeStory()
    var typeService = preferManager.getPreferenceService()
    val typeName = Gson().fromJson(typeStory, TypeStoryModel::class.java)
    val serviceList = Gson().fromJson(typeService, ServiceList::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRecycleViewBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: RequestCommonApi.Data) {
            var messageTypeStatus = item.messageTypeStatus // service radio buttom
            var messageTypeId = item.messageTypeId // id dropdown
            var serviceId = item.srctype // id dropdown

            var (displayName,color,serviceName) = getName(messageTypeStatus, messageTypeId,serviceId)
            binding.apply {
                typeCalling.text = displayName
                typeCalling.setTextColor(Color.parseColor(color))
                message.text = item.summaryAgent
                agent.text = item.agentimport
                srcValue.text = item.srcFirstName
                channal.text = serviceName
                dst.text = item.dstFirstName
                dateTime.text = item.datebegin

                cardOnClick.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
                delete.setOnClickListener {
                    onItemClickListenerDeleteEmergency?.let {
                        Log.i("del", "runningProgram: ")
                        it(item)
                    }
                }
            }
        }
    }

    fun getName(messageTypeStatus: Int?, messageTypeId: Int?, serviceId: Int?): Triple<String?, String?,String?> {
        var text:String? = "(ไม่ได้ระบุ)"
        var color:String? = "#00FF00"
        var serviceName:String? = "123"
        when (messageTypeStatus) {
            0 -> {
                val typeMaxsize = typeName.data!!.incompleteType.size
                for (i in 0 until typeMaxsize) {
                    val typeId = typeName.data!!.incompleteType[i].typeId
                    if (typeId == messageTypeId.toString()) {
                        text = typeName.data!!.incompleteType[i].typeText.toString()
                        color = "#FD9701"
                    }
                }
            }
            1 -> {
                val typeMaxsize = typeName.data!!.completeType.size
                for (i in 0 until typeMaxsize) {
                    val typeId = typeName.data!!.completeType[i].typeId
                    if (typeId == messageTypeId.toString()) {
                        text = typeName.data!!.completeType[i].typeText.toString()
                        color = "#00FF00"
                    }
                }
            }
            2 -> {
                val typeMaxsize = typeName.data!!.noserviceType.size
                for (i in 0 until typeMaxsize) {
                    val typeId = typeName.data!!.noserviceType[i].typeId
                    if (typeId == messageTypeId.toString()) {
                        //text = typeName.data!!.noserviceType[i].typeText.toString()
                        text = typeName.data!!.noserviceType[i].typeText.toString()
                        color = "#FF0000"
                    }
                }
            }
        }
        val typeMaxService = serviceList.data.size
        for (i in 0 until typeMaxService){
            val serviceIdList = serviceList.data[i].id
            if (serviceId == serviceIdList){
                serviceName = serviceList.data[i].name
            }
        }
        return Triple(text,color,serviceName)
    }


    private var onItemClickListener: ((RequestCommonApi.Data) -> Unit)? = null

    private var onItemClickListenerDeleteEmergency: ((RequestCommonApi.Data) -> Unit)? = null

    fun setOnItemClickListener(listener: (RequestCommonApi.Data) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemClickListenerDeleteEmergency(listener: (RequestCommonApi.Data) -> Unit) {
        onItemClickListenerDeleteEmergency = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<RequestCommonApi.Data>() {
            override fun areItemsTheSame(
                oldItem: RequestCommonApi.Data,
                newItem: RequestCommonApi.Data
            ): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(
                oldItem: RequestCommonApi.Data,
                newItem: RequestCommonApi.Data
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}