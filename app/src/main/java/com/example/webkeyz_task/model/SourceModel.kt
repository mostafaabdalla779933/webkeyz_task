package com.example.webkeyz_task.model

import com.google.gson.annotations.SerializedName

data class SourceModel(


    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Any? = null
)