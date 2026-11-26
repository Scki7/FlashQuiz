package com.example.flashquiz.data.remote

import com.example.flashquiz.data.remote.dto.TriviaResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApi {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("type") type: String
    ): TriviaResponseDto
}