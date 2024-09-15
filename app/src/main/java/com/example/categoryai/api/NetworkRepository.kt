// Import necessary packages
package com.example.categoryai.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class NetworkRepository {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    // Existing function
    suspend fun getRelatedCategories(title: String, description: String): List<Category>? {
        val url = "http://10.0.2.2:8080/popup/ai/category"
        val apiKey = "sk-proj-VXwcPzlcS0gr1vjm62FVT3BlbkFJHgphK3z9HJbEJ0JdTLyw"

        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post(url) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $apiKey")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(
                        CategoryAi(
                            title = title,
                            description = description
                        )
                    )
                }

                val result: List<ResultItem> = Json.decodeFromString(response.bodyAsText())
                Log.d("NetworkRepository", "Response from local API: ${result.flatMap { it.categories }}")

                result.flatMap { it.categories }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NetworkRepository", "Failed to fetch related categories", e)
                null
            }
        }
    }

    // New function for category recommendation
    suspend fun getRecommendedCategories(): List<CategoryRc>? {
        val url = "http://10.0.2.2:8080/popup/ai/recommend/category"
        val apiKey = "sk-proj-VXwcPzlcS0gr1vjm62FVT3BlbkFJHgphK3z9HJbEJ0JdTLyw"

        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = client.post(url) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $apiKey")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                }

                val result: CategoryRecommend = response.body()

                Log.d("NetworkRepository", "Response: ${result.data}")

                result.data
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NetworkRepository", "Failed to fetch recommended categories", e)
                null
            }
        }
    }
}
