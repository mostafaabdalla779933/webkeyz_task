package com.example.webkeyz_task.Data.remote

import com.example.webkeyz_task.model.NewsResponse
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*

interface APIService {


    @GET
    suspend fun getRequest(
        @Url api: String,
        @QueryMap map:HashMap<String,Any>,
    ): Response<NewsResponse>

    @POST
    @JvmSuppressWildcards
    suspend fun postRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @Body body: Map<String, Any>?
    ): Response<JsonElement>

    @DELETE
    suspend fun deleteRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?
    ): Response<JsonElement>


}