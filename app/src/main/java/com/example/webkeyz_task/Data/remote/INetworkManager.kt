package com.example.webkeyz_task.Data.remote

import com.example.webkeyz_task.model.NewsResponse
import retrofit2.Response

interface INetworkManager {
    suspend fun getRequest(
        api: String,
        page: String
    ): Response<NewsResponse>
}