package com.ezatpanah.hilt_retrofit_paging_youtube.Unsaved.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.Component.SharePreferenceManager
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Unsaved.Model.PetitionUnSaveModel
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.AdapterCardApiBinding
import com.google.gson.Gson
import javax.inject.Inject


class UnsavedAdapter @Inject() constructor(
    private var preferManager: SharePreferenceManager

) :
    PagingDataAdapter<PetitionUnSaveModel.Agenttest01, UnsavedAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: AdapterCardApiBinding
    private lateinit var context: Context

    var typeStory = preferManager.getPreferenceTypeStory()
    var typeService = preferManager.getPreferenceService()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = AdapterCardApiBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: PetitionUnSaveModel.Agenttest01) {
            binding.apply {
                tvPhone.text = item.src
                tvPhone.setTextColor(Color.parseColor("#4CAE50"))
                tvTypePhone.text = item.serviceName
                tvDate.text = item.datebegin
                cardOnClick.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }


    private var onItemClickListener: ((PetitionUnSaveModel.Agenttest01) -> Unit)? = null


    fun setOnItemClickListener(listener: (PetitionUnSaveModel.Agenttest01) -> Unit) {
        onItemClickListener = listener
    }


    companion object {
        val differCallback = object : DiffUtil.ItemCallback<PetitionUnSaveModel.Agenttest01>() {
            override fun areItemsTheSame(
                oldItem: PetitionUnSaveModel.Agenttest01,
                newItem: PetitionUnSaveModel.Agenttest01
            ): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(
                oldItem: PetitionUnSaveModel.Agenttest01,
                newItem: PetitionUnSaveModel.Agenttest01
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}