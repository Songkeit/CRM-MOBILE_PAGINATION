package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.response.RequestCommonApi
import retrofit2.HttpException


class UnsavedPagingSource(
    private val repository: ApiRepository,
) : PagingSource<Int, PetitionUnSaveModel.Agenttest01>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PetitionUnSaveModel.Agenttest01> {
        return try {
            // add page +=1
            val response = repository.listUnsaved()
            val data = response.body()!!.data!!.agenttest01

            val responseData = mutableListOf<PetitionUnSaveModel.Agenttest01>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey = null
                //currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, PetitionUnSaveModel.Agenttest01>): Int? {
        return null
    }


}