package com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model

import com.google.gson.annotations.SerializedName

data class ServiceList(
    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()
) {
    data class Data(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("iqm_id") var iqmId: Int? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("asterisk_key_name") var asteriskKeyName: String? = null,
        @SerializedName("service_type_name") var serviceTypeName: String? = null,
        @SerializedName("is_show") var isShow: Int? = null

    )
}