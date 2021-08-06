package com.example.webkeyz_task.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticleModel>? = null,

	@field:SerializedName("status")
	val status: String? = null
)




