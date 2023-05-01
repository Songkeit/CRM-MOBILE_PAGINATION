package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import com.google.gson.annotations.SerializedName

data class ContactSearchModel(
    @SerializedName("status") var status: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()
) {

    data class Data(
        @SerializedName("name") var name: String? = null,
        @SerializedName("lastname") var lastname: String? = null,
        @SerializedName("phone") var phone: String? = null

    )
}