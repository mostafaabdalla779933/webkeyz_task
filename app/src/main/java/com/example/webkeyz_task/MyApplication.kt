package com.example.webkeyz_task

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {

    init {
        instance = this
    }
    companion object {
        private var instance: MyApplication? = null
        fun getAppContext(): Context {
            return instance!!.applicationContext
        }
    }
}
