package com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model

import com.google.gson.annotations.SerializedName

data class TypeStoryModel(
    @SerializedName("data") var data: Data? = Data(),
    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: String? = null
) {
    data class Data(

        @SerializedName("completeType") var completeType: ArrayList<CompleteType> = arrayListOf(),
        @SerializedName("incompleteType") var incompleteType: ArrayList<IncompleteType> = arrayListOf(),
        @SerializedName("noserviceType") var noserviceType: ArrayList<NoserviceType> = arrayListOf()

    )

    data class NoserviceType(

        @SerializedName("type_id") var typeId: String? = null,
        @SerializedName("type_text") var typeText: String? = null,
        @SerializedName("order") var order: String? = null,
        @SerializedName("is_emergency") var isEmergency: String? = null,
        @SerializedName("is_test") var isTest: String? = null

    )

    data class IncompleteType(

        @SerializedName("type_id") var typeId: String? = null,
        @SerializedName("type_text") var typeText: String? = null,
        @SerializedName("order") var order: String? = null,
        @SerializedName("is_emergency") var isEmergency: String? = null,
        @SerializedName("is_test") var isTest: String? = null

    )

    data class CompleteType(

        @SerializedName("type_id") var typeId: String? = null,
        @SerializedName("type_text") var typeText: String? = null,
        @SerializedName("order") var order: String? = null,
        @SerializedName("is_emergency") var isEmergency: String? = null,
        @SerializedName("is_test") var isTest: String? = null

    )
}