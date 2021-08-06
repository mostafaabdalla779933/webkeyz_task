package com.example.webkeyz_task.repo

import com.example.webkeyz_task.Data.remote.NetworkManager
import com.example.webkeyz_task.Data.remote.EndPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepo @Inject constructor() {


    @Inject
    lateinit var networkManager: NetworkManager

    suspend fun fetchPosts(start : String) = withContext(Dispatchers.IO) {
        networkManager.getRequest(EndPoint.news,start)
    }
}

