package com.example.webkeyz_task.Data.remote

import com.example.webkeyz_task.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NetworkManager @Inject constructor(private val apiService : APIService)  {

    companion object{
        const val size = 20
        const val maxSize = 100
    }

    suspend fun  getRequest(
        api: String,
        page :String
    ): Response<NewsResponse> = withContext(Dispatchers.IO) {
       val map = hashMapOf<String ,Any>(
            "page" to page
       )
        apiService.getRequest(api,map)
    }
}