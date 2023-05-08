package com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Model

import com.google.gson.annotations.SerializedName


data class ProvinceModel(
    @SerializedName("_metadata") var metadata: Metadata? = Metadata(),
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: String? = null
) {
    data class Metadata(
        @SerializedName("page") var page: Int? = null,
        @SerializedName("per_page") var perPage: Int? = null,
        @SerializedName("page_count") var pageCount: Int? = null,
        @SerializedName("total_count") var totalCount: Int? = null
    )


    data class Data(
        @SerializedName("extension") var extension: String? = null,
        @SerializedName("phonenumber") var phonenumber: String? = null,
        @SerializedName("display_name") var displayName: String? = null,
        @SerializedName("province_name") var provinceName: String? = null,
        @SerializedName("hospital_name") var hospitalName: String? = null,
        @SerializedName("location_address") var locationAddress: String? = null,
        @SerializedName("location_setup") var locationSetup: String? = null,
        @SerializedName("latitude") var latitude: String? = null,
        @SerializedName("longitude") var longitude: String? = null,
        @SerializedName("nwise_account") var nwiseAccount: String? = null,
        @SerializedName("group_id") var groupId: String? = null,
        @SerializedName("is_show") var isShow: String? = null,
        @SerializedName("created_at") var createdAt: String? = null,
        @SerializedName("updated_at") var updatedAt: String? = null
    )
}