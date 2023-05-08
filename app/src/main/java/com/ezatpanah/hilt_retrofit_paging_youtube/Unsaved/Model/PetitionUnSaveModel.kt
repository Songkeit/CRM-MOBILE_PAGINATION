package com.ezatpanah.hilt_retrofit_paging_youtube.Unsaved.Model

import com.google.gson.annotations.SerializedName

data class PetitionUnSaveModel (
    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: Data? = Data()
) {

    data class Agenttest01(

        @SerializedName("id") var id: String? = null,
        @SerializedName("refid") var refid: String? = null,
        @SerializedName("video_id") var videoId: String? = null,
        @SerializedName("service_name") var serviceName: String? = null,
        @SerializedName("src") var src: String? = null,
        @SerializedName("identification") var identification: String? = null,
        @SerializedName("src_first_name") var srcFirstName: String? = null,
        @SerializedName("src_last_name") var srcLastName: String? = null,
        @SerializedName("dst") var dst: String? = null,
        @SerializedName("callout_detail") var calloutDetail: String? = null,
        @SerializedName("dst_first_name") var dstFirstName: String? = null,
        @SerializedName("dst_last_name") var dstLastName: String? = null,
        @SerializedName("calltype") var calltype: String? = null,
        @SerializedName("service_group") var serviceGroup: String? = null,
        @SerializedName("servicetype") var servicetype: String? = null,
        @SerializedName("logtype") var logtype: String? = null,
        @SerializedName("is_emergency") var isEmergency: String? = null,
        @SerializedName("kiosk_id") var kioskId: String? = null,
        @SerializedName("kiosk_name") var kioskName: String? = null,
        @SerializedName("srctype") var srctype: String? = null,
        @SerializedName("blackmonitor") var blackmonitor: String? = null,
        @SerializedName("message") var message: String? = null,
        @SerializedName("summary_agent") var summaryAgent: String? = null,
        @SerializedName("message_type_status") var messageTypeStatus: String? = null,
        @SerializedName("message_type_id") var messageTypeId: String? = null,
        @SerializedName("other_detail") var otherDetail: String? = null,
        @SerializedName("agentimport") var agentimport: String? = null,
        @SerializedName("created_at") var createdAt: String? = null,
        @SerializedName("updated_at") var updatedAt: String? = null,
        @SerializedName("datebegin") var datebegin: String? = null,
        @SerializedName("dateagent") var dateagent: String? = null,
        @SerializedName("datepickup") var datepickup: String? = null,
        @SerializedName("datecallout") var datecallout: String? = null,
        @SerializedName("dateend") var dateend: String? = null,
        @SerializedName("insert_status") var insertStatus: String? = null,
        @SerializedName("delete_status") var deleteStatus: String? = null

    )

    data class Data(

        @SerializedName("agenttest01") var agenttest01: ArrayList<Agenttest01> = arrayListOf()

    )

}