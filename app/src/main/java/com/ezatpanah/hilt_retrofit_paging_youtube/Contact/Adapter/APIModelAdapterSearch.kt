package com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Model.ContactSearchModel


class APIModelAdapterSearch(
    var resultsSearch: ArrayList<ContactSearchModel.Data>?
) :
    RecyclerView.Adapter<APIModelAdapterSearch.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.card_dialog_list_search,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resultSearch = resultsSearch?.get(position)
        var fullName = resultSearch!!.name + resultSearch.lastname
        resultSearch.let {
            holder.view.findViewById<TextView>(R.id.displayFname).text = fullName
            holder.view.findViewById<TextView>(R.id.video_phone_search).text = resultSearch!!.phone
        }
    }


    override fun getItemCount(): Int {
        resultsSearch?.let {
            return it.size
        }
        return 0
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(data: List<ContactSearchModel.Data>) {//
        this.resultsSearch!!.clear()
        this.resultsSearch!!.addAll(data)
        this.notifyDataSetChanged()
    }

}