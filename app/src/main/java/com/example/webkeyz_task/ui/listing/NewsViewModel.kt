package com.example.webkeyz_task.ui.listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.webkeyz_task.model.ArticleModel
import com.example.webkeyz_task.repo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repo: NewsRepo): ViewModel() {

    var postsList  = MutableLiveData<MutableList<ArticleModel>>()
    var error = MutableLiveData<Boolean>()
    var stateLiveData = MutableLiveData<String?>()
    var list = mutableListOf<ArticleModel>()
    var page = 0


    fun fetchPosts(){
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch{
            page ++
           val response = repo.fetchPosts(page.toString())
            handleError(response.code())
            if (response.isSuccessful){
                list = postsList.value ?: mutableListOf()
                response.body()?.articles?.let { news ->
                    list.addAll(news.toMutableList() )
                    postsList.postValue(list)
                    error.postValue(false)
                }
            }else{
                error.postValue(true)
            }
        }
    }

    // handle api response code
    private fun handleError(code : Int){
        when {
            code == 0 ->{
                error.postValue(true)
                stateLiveData.postValue("check you internet connection")
            }
            code in 200..399 -> {
                stateLiveData.postValue(null)
            }
            code in 400..499 -> {
                // client error
                error.postValue(true)
                stateLiveData.postValue("connection failed")
            }
            code >= 500 -> {
                error.postValue(true)
                stateLiveData.postValue("server problem")
            }
        }
    }


    private val coroutineExceptionHandler= CoroutineExceptionHandler{ _, _ ->
        handleError(0)
    }
}