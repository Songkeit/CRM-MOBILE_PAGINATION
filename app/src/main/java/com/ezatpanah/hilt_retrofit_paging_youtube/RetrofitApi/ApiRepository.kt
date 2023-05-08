package com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi

import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.CreateUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Model.ApiInformationUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Model.DeleteData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    suspend fun getEmergency(page: Int, search: String?) = apiServices.getEmergency(
        page.toString(),
        "25",
        search,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        "0",
        null,
        "vrs"
    )

    suspend fun getNormal(page: Int, search: String?, stateCheckDelete: String?,) = apiServices.getNormal(
        page.toString(),
        "25",
        search,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        stateCheckDelete!!,
        null,
        "vrs"
    )
    suspend fun getUserDetails(id: Int) = apiServices.getUserDetails(id.toString())
    suspend fun createData(data: CreateUser.Data) = apiServices.createData(data)
    suspend fun updateData(data: ApiInformationUser.Data) = apiServices.updateData(data)
    suspend fun deleteEmergency(id: DeleteData.Data) = apiServices.delete(id)// status have 1
    suspend fun serviceListDropdown() = apiServices.serviceList()
    suspend fun kioskDropdown() = apiServices.kioskList()
    suspend fun seatsVrs() = apiServices.seatsVrs()
    suspend fun contactProvince(search:String) = apiServices.contactProvince(
        null,
        search,
        "1",
        "200"
    )
    suspend fun typeStory() = apiServices.typeStory()
    suspend fun contactSearch(search:String) = apiServices.contactSearch(search)
    suspend fun searchAgency(search:String) = apiServices.searchAgency(search,"100")
    suspend fun listUnsaved() = apiServices.listUnsaved()

}