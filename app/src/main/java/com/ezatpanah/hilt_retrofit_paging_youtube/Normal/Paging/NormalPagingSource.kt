package com.ezatpanah.hilt_retrofit_paging_youtube.Normal.Paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.response.RequestCommonApi
import retrofit2.HttpException

class NormalPagingSource(
    private val repository: ApiRepository,
    private val search: String?,
    private val stateCheckDelete: String?,
) : PagingSource<Int, RequestCommonApi.Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RequestCommonApi.Data> {
        return try {
            // add page +=1
            val currentPage = params.key ?: 1
            val response = repository.getNormal(currentPage,search,stateCheckDelete)
            val data = response.body()!!.data
            Log.i("D1", "load: $data")

            val responseData = mutableListOf<RequestCommonApi.Data>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = if (data.isEmpty()) null else currentPage.plus(1)
                //currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, RequestCommonApi.Data>): Int? {
        return null
    }


}