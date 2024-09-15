package com.example.categoryai.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRecommend(
    @SerialName("data")
    val `data`: List<CategoryRc>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: Boolean
)


@Serializable
data class CategoryRc(
    @SerialName("categories")
    val categories: List<Category>,
    @SerialName("id")
    val id: Int,
    @SerialName("popupImages")
    val popupImages: List<String>,
    @SerialName("title")
    val title: String
)

