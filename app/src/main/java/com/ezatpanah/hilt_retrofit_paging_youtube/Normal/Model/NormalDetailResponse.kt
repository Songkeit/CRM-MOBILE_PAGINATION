package com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Model

import com.google.gson.annotations.SerializedName

data class NormalDetailResponse( // list notmal -> detail
    @SerializedName("status") var status: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("countpage") var countpage: Int? = null,
    @SerializedName("count_row") var countRow: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()

) {
    data class Data(

        @SerializedName("id") var id: Int? = null,//
        @SerializedName("refid") var refid: String? = null,//
        @SerializedName("video_id") var videoId: Int? = null,//
        @SerializedName("is_emergency") var isEmergency: Int? = null,
        @SerializedName("watingtime") var watingtime: Int? = null,
        @SerializedName("servicetime") var servicetime: String? = null,
        @SerializedName("totaltime") var totaltime: String? = null,
        @SerializedName("service_name") var serviceName: String? = null,
        @SerializedName("src") var src: String? = null,//
        @SerializedName("src_first_name") var srcFirstName: String? = null,//
        @SerializedName("src_last_name") var srcLastName: String? = null,//
        @SerializedName("dst") var dst: String? = null,//
        @SerializedName("dst_first_name") var dstFirstName: String? = null,//
        @SerializedName("dst_last_name") var dstLastName: String? = null,//
        @SerializedName("summary_agent") var summaryAgent: String? = null,
        @SerializedName("message_type_status") var messageTypeStatus: Int? = null,
        @SerializedName("message_type_id") var messageTypeId: Int? = null,
        @SerializedName("srctype") var srctype: Int? = null,
        @SerializedName("agentimport") var agentimport: String? = null,
        @SerializedName("datebegin") var datebegin: String? = null,
        @SerializedName("insert_status") var insertStatus: Int? = null,
        @SerializedName("created_at") var createdAt: String? = null

    )
}