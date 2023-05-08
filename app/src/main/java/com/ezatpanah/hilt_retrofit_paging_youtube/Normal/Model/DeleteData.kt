package com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Model

import com.google.gson.annotations.SerializedName

data class DeleteData(
    @SerializedName("status") var status: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: Data? = Data(),

    ) {
    data class Data(
        @SerializedName("delete_status") var deleteStatusd: String? = null,
        @SerializedName("id") var id: String? = null,
    )
}
