package com.example.categoryai.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val categories: List<ResultItem>
)

@Serializable
data class ResultItem(
    @SerialName("categories")
    val categories: List<Category>,
)

@Serializable
data class CategoryAi(
    @SerialName("description")
    val description: String,
    @SerialName("title")
    val title: String
)

@Serializable
data class Category(
    @SerialName("category")
    val category: String
)