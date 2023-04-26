package com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.ItemMoviesBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.response.RequestCommonApi
import javax.inject.Inject


class NormalAdapter @Inject() constructor() :
    PagingDataAdapter<RequestCommonApi.Data, NormalAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemMoviesBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMoviesBinding.inflate(inflater, parent, false)
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
            binding.apply {
                agent.text = item.agentimport
                dateTime.text = item.datebegin
                srcValue.text = item.srcFirstName
                dst.text = item.dstFirstName
                message.text = item.summaryAgent

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
                delete.setOnClickListener{
                    onItemClickListenerDeleteNormal?.let {
                        Log.i("del", "runningProgram: ")
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((RequestCommonApi.Data) -> Unit)? = null

    private var onItemClickListenerDeleteNormal: ((RequestCommonApi.Data) -> Unit)? = null


    fun setOnItemClickListener(listener: (RequestCommonApi.Data) -> Unit) {
        onItemClickListener = listener
    }
    fun setOnItemClickListenerDeleteNormal(listener: (RequestCommonApi.Data) -> Unit) {
        onItemClickListenerDeleteNormal = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<RequestCommonApi.Data>() {
            override fun areItemsTheSame(oldItem: RequestCommonApi.Data, newItem: RequestCommonApi.Data): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: RequestCommonApi.Data, newItem: RequestCommonApi.Data): Boolean {
                return oldItem == newItem
            }
        }
    }

}