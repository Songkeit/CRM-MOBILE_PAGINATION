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
    fun typeStoryAdapter() = apiServices.typeStoryAdapter()
    suspend fun getDataApiPetitionID(id: String) = apiServices.getPetitionId(id)// status have 1

    companion object{
        private val client: OkHttpClient
            get() {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                return OkHttpClient.Builder()
                    //.connectTimeout(30,TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()
            }

        val BASE_URL_TYPESTORY: String = "https://dev-api.ttrs.in.th/"
        val endpointstypestory: ApiServices
            get() {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL_TYPESTORY)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(ApiServices::class.java)

            }

        val BASE_URL_SserviceType:String = "https://dev-apiicrm.ttrs.in.th/"
        val endpointservicetype: ApiServices
            get() {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL_SserviceType)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(ApiServices::class.java)

            }

        val BASE_URL_KioskList: String = "https://dev-api.ttrs.in.th/"
        val endpointkiosklist: ApiServices
            get() {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL_KioskList)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(ApiServices::class.java)

            }
        val BASE_URL_POST: String = "https://dev-apiicrm.ttrs.in.th/"
        fun getRetrofitInstanceCreateData(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(BASE_URL_POST)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    val BASE_URL_DROP: String = "https://dev-api.ttrs.in.th/"
    val endpointdrop: ApiRepository
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_DROP)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiRepository::class.java)

        }
    val BASE_URL_SserviceType:String = "https://dev-apiicrm.ttrs.in.th/"
    val endpointservicetype: ApiServices
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_SserviceType)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiServices::class.java)

        }
}