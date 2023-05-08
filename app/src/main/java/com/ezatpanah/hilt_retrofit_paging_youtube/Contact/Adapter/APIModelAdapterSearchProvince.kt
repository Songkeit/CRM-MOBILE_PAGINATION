package com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Model.ProvinceModel


class APIModelAdapterSearchProvince(
    var resultsProvince: ArrayList<ProvinceModel.Data>

) :
    RecyclerView.Adapter<APIModelAdapterSearchProvince.ViewHolder>() {

    private lateinit var displayName:TextView
    private lateinit var videoPhone:TextView
    private lateinit var numberPhone:TextView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.card_dialog_list,
            parent, false
        )
    )

    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resultProvince = resultsProvince[position]
        displayName = holder.view.findViewById(R.id.displayName)
        resultProvince.let {
            var phoneNumber = resultProvince.phonenumber
            var displayName = resultProvince.displayName
            var extension = resultProvince.extension
            if (resultProvince.phonenumber!!.isEmpty()){
                phoneNumber = "-"
            }
            if (resultProvince.displayName!!.isEmpty()){
                displayName = "-"
            }
            if (resultProvince.extension!!.isEmpty()){
                extension = "-"
            }
//            holder.view.displayName.text = displayName
//            holder.view.video_phone.text = extension
//            holder.view.number_phone.text = phoneNumber
            holder.view.findViewById<TextView>(R.id.displayName).text = displayName.toString()
            holder.view.findViewById<TextView>(R.id.video_phone).text = extension.toString()
            holder.view.findViewById<TextView>(R.id.number_phone).text = phoneNumber.toString()

        }
        holder.view.findViewById<ImageView>(R.id.copy_video_phone).setOnClickListener{
            copyTextToClipboard(holder.itemView.context,holder.view.findViewById<TextView>(R.id.video_phone).text.toString())
            Log.i("copy", "onBindViewHolder: ${holder.view.findViewById<TextView>(R.id.video_phone).text}")
            Toast.makeText(holder.itemView.context,"Clipboard",Toast.LENGTH_SHORT).show()
        }
        holder.view.findViewById<ImageView>(R.id.copy_number_phone).setOnClickListener{
            copyTextToClipboard(holder.itemView.context,holder.view.findViewById<TextView>(R.id.number_phone).text.toString())
            Log.i("copy", "onBindViewHolder: ${holder.view.findViewById<TextView>(R.id.number_phone).text}")
            Toast.makeText(holder.itemView.context,"Clipboard",Toast.LENGTH_SHORT).show()

        }

    }


    override fun getItemCount(): Int {
        return resultsProvince.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(data: List<ProvinceModel.Data>) {//
        this.resultsProvince.clear()
        this.resultsProvince.addAll(data)
        this.notifyDataSetChanged()
    }
    fun copyTextToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", text)
        clipboard.setPrimaryClip(clip)
    }


}
