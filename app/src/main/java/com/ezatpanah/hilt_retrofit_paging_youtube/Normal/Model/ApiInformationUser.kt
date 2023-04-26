package com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class ApiInformationUser(
    @SerializedName("status") var status: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: Data? = Data()
) {
    data class Data(
        @SerializedName("id") var id: String? = null,
        @SerializedName("refid") var refid: String? = null,
        @SerializedName("video_id") var videoId: String? = null,
        @SerializedName("src") var src: String? = null,
        @SerializedName("src_first_name") var srcFirstName: String? = null,
        @SerializedName("src_last_name") var srcLastName: String? = null,
        @SerializedName("dst") var dst: String? = null,
        @SerializedName("dst_first_name") var dstFirstName: String? = null,
        @SerializedName("dst_last_name") var dstLastName: String? = null,
        @SerializedName("calltype") var calltype: String? = null,
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
        @SerializedName("datebegin") var datebegin: String? = null,
        @SerializedName("dateagent") var dateagent: String? = null,
        @SerializedName("datepickup") var datepickup: String? = null,
        @SerializedName("datecallout") var datecallout: String? = null,
        @SerializedName("dateend") var dateend: String? = null,
        @SerializedName("insert_status") var insertStatus: String? = null,
        @SerializedName("delete_status") var deleteStatus: String? = null,
        @SerializedName("technical") var technical: Technical? = Technical(),
        @SerializedName("file") var file: File? = File()

    ) {

        data class File(
            @SerializedName("video_id") var videoId: String? = null,
            @SerializedName("name") var name: String? = null,
            @SerializedName("path") var path: String? = null,
            @SerializedName("backup_status") var backupStatus: String? = null,
            @SerializedName("backup_url") var backupUrl: String? = null,
            @SerializedName("url") var url: String? = null

        )

        data class Technical(
            var technical: ArrayList<String>? = null
        )
    }


}
