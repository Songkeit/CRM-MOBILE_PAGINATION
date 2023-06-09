package com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi


import com.ezatpanah.hilt_retrofit_paging_youtube.ChooseService.Model.SeatsModelApi
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Model.ContactSearchModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Model.ProvinceModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.CreateUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.KioskList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Model.ApiInformationUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Model.DeleteData
import com.ezatpanah.hilt_retrofit_paging_youtube.Unsaved.Model.PetitionUnSaveModel
import com.ezatpanah.hilt_retrofit_paging_youtube.response.RequestCommonApi
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @GET("petitionEmergency?")
    suspend fun getEmergency(
        @Query("page") page: String,//1,2,3,4
        @Query("pageshow") pageshow: String,//25 50 100..
        @Query("keyword_search_all") keyword_search_all: String?, // จะต้องมีkey word การ search
        @Query("src") src: String?,
        @Query("summary_agent") summary_agent: String?,
        @Query("agentimport") agentimport: String?,
        @Query("src_first_name") src_first_name: String?,
        @Query("startdate") startdate: String?,
        @Query("enddate") enddate: String?,
        @Query("message_type_status") message_type_status: String?,
        @Query("message_type_id") message_type_id: String?,
        @Query("srctype") srctype: String?,
        @Query("delete_status") delete_status: String,//0
        @Query("insert_status") insert_status: String?,
        @Query("service_name") service_name: String, // vrs

    ): Response<RequestCommonApi>// emergency,RequestCommonAPI

    @GET("petitionList?")
    suspend fun getNormal(
        @Query("page") page: String,//1,2,3,4
        @Query("pageshow") pageshow: String,//25 50 100..
        @Query("keyword_search_all") keyword_search_all: String?, // จะต้องมีkey word การ search
        @Query("src") src: String?,
        @Query("summary_agent") summary_agent: String?,
        @Query("agentimport") agentimport: String?,
        @Query("src_first_name") src_first_name: String?,
        @Query("startdate") startdate: String?,
        @Query("enddate") enddate: String?,
        @Query("message_type_status") message_type_status: String?,
        @Query("message_type_id") message_type_id: String?,
        @Query("srctype") srctype: String?,
        @Query("delete_status") delete_status: String,//0
        @Query("insert_status") insert_status: String?,
        @Query("service_name") service_name: String, // vrs

    ): Response<RequestCommonApi>// emergency,RequestCommonAPI

    // delete Emergency
    @PUT("petitionStatus")
    suspend fun delete(@Body params: DeleteData.Data): Response<DeleteData> // message status deta(delStatus, id)

    @GET("petition/{id}")
    suspend fun getUserDetails(@Path("id") id: String): Response<ApiInformationUser>//getPetitionId,ApiInformaionUser.Data

    //update data normal and emergency
    @PUT("petition")
    suspend fun updateData(@Body params: ApiInformationUser.Data): Response<ApiInformationUser>

    //Create new user
    @POST("petition")
    suspend fun createData(@Body params: CreateUser.Data): Response<CreateUser.ResponseUser>

    @GET("serviceList")
    suspend fun serviceList(): Response<ServiceList>

    @GET("https://dev-api.ttrs.in.th/v3/services/kiosks?pagination=false")
    suspend fun kioskList(): Response<KioskList>

    //Seat
    @GET("https://dev-api.ttrs.in.th/v3/services/8/seats")
    suspend fun seatsVrs(): Response<SeatsModelApi>

    //contact
    @GET("https://dev-api.ttrs.in.th/v3/services/videophones?")
    suspend fun contactProvince(
        @Query("pagination") pagination: String?, // default = false if data not empty
        @Query("search") search: String?, //input
        @Query("group_id") group_id: String?, // 1
        @Query("per_page") per_page: String?, // 200
    ): Response<ProvinceModel>

    //contact people
    @GET("https://dev-apiicrm.ttrs.in.th/destinationList?")
    suspend fun contactSearch(
        @Query("keyword_search_all") keyword_search_all: String?,
        ):Response<ContactSearchModel>

    //contact
    @GET("https://dev-api.ttrs.in.th//v3/services/videophones")
    suspend fun searchAgency(
        @Query("search") search: String?, //input
        @Query("per_page") per_page: String?, // 100
    ):Response<ProvinceModel>

    //dropDown
    @GET("https://dev-api.ttrs.in.th/v3/petitions/services/VRS?all=true")
    suspend fun typeStory(): Response<TypeStoryModel>


    //unsaved
    @GET("https://dev-apiicrm.ttrs.in.th/petitionUnsave?agentimport=agenttest01&service_name=vrs")
    suspend fun listUnsaved(): Response<PetitionUnSaveModel>





}