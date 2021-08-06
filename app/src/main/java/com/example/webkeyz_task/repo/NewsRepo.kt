package com.example.webkeyz_task.repo

import com.example.webkeyz_task.Data.remote.EndPoint
import com.example.webkeyz_task.Data.remote.INetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepo @Inject constructor(val networkManager: INetworkManager) {

    suspend fun fetchPosts(page : String) = withContext(Dispatchers.IO) {
        networkManager.getRequest(EndPoint.news,page)
    }
}

