package com.example.webkeyz_task.Data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(val shared : SharedPreferences){

    fun putPage(page :Int ) {
        shared.edit().putInt("page",page).commit()
    }

    fun getPage(): Int = shared.getInt("page",1)

}