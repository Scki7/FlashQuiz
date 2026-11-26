package com.example.flashquiz.data.repository

import com.example.flashquiz.data.model.Flashcard
import com.example.flashquiz.data.remote.RetrofitClient
import com.example.flashquiz.data.remote.TriviaApi
import com.example.flashquiz.data.remote.dto.toFlashcard
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class TriviaRepository(
    private val api: TriviaApi = RetrofitClient.triviaApi
) {

    suspend fun getQuestions(amount: Int, categoryId: Int): Result<List<Flashcard>> {
        return try {
            val response = api.getQuestions(
                amount = amount,
                category = categoryId,
                type = "multiple"
            )
            when (response.responseCode) {
                0, 1 -> Result.success(
                    response.results.mapIndexed { index, dto -> dto.toFlashcard(index + 1) }
                )
                5 -> Result.failure(Exception("Too many requests. Wait 5 seconds and press Retry."))
                else -> Result.failure(Exception("Server returned error code ${response.responseCode}."))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            if (e.code() == 429) {
                Result.failure(Exception("Too many requests. Wait 5 seconds and press Retry."))
            } else {
                Result.failure(Exception("Server error ${e.code()}."))
            }
        } catch (e: IOException) {
            Result.failure(Exception("No internet connection. Check your network and press Retry."))
        } catch (e: Exception) {
            Result.failure(Exception("Could not read the server response."))
        }
    }
}