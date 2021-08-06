package com.example.webkeyz_task.ui.listing

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.webkeyz_task.Data.local.SharedPref
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
    var _page = 1


    fun fetchPosts(){
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch{
           val response = repo.fetchPosts(_page.toString())
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
        Log.i("main", "handleError: $code")
        when {
            code == 0 ->{
                error.postValue(true)
                stateLiveData.postValue("check you internet connection")
            }
            code in 200..399 -> {
                stateLiveData.postValue(null)
              //  stateLiveData.postValue("connection success")
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


    private val coroutineExceptionHandler= CoroutineExceptionHandler{ _, thro ->
        handleError(0)
    }
}