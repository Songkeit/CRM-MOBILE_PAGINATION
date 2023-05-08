package com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Model.ProvinceModel


class APIModelAdapterAgency(
    var resultsAgency: ArrayList<ProvinceModel.Data>?

) :
    RecyclerView.Adapter<APIModelAdapterAgency.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.card_dialog_list_search,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // หน่วยงาน
        val resultAgency = resultsAgency?.get(position)
        resultAgency.let {
            var phoneNumber = resultAgency!!.phonenumber
            if (resultAgency!!.phonenumber!!.isEmpty()) {
                phoneNumber = "-"
            }
            holder.view.findViewById<TextView>(R.id.displayFname).text = resultAgency!!.displayName
            holder.view.findViewById<TextView>(R.id.video_phone_search).text = phoneNumber
            holder.view.findViewById<TextView>(R.id.extentionValue).text = resultAgency!!.extension
        }
    }


    override fun getItemCount(): Int {
        resultsAgency?.let {
            return it.size
        }
        return 0
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(data: List<ProvinceModel.Data>) {//
        this.resultsAgency!!.clear()
        this.resultsAgency!!.addAll(data)
        this.notifyDataSetChanged()
    }

}