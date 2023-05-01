package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import com.google.gson.annotations.SerializedName

data class SeatsModelApi(
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: String? = null
) {

    data class Data(

        @SerializedName("id") var id: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("room") var room: String? = null

    )
}