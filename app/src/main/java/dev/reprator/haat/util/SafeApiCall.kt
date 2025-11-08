package dev.reprator.haat.util

import com.fasterxml.jackson.databind.ObjectMapper
import dev.reprator.haat.features.restaurant2pane.restaurants.data.remote.modal.EntityError
import retrofit2.HttpException

suspend fun <T> safeApiCall(mapper: ObjectMapper, apiCall: suspend () -> T): AppResult<T> {
    return try {
        AppSuccess(apiCall.invoke())
    } catch (e: Exception) {
        val message = if (e is HttpException) {
             e.toApiError(mapper)?.message ?: e.message ?: ""
        } else {
            e.message ?: ""
        }
        AppError(message = message)
    }
}

fun HttpException.errorBodyString(): String? {
    return response()?.errorBody()?.string()
}

fun HttpException.toApiError(mapper: ObjectMapper): EntityError? {
    val body = errorBodyString() ?: return null
    if (body.isBlank()) return null

    return try {
        mapper.readValue(body, EntityError::class.java)
    } catch (e: Exception) {
        null
    }
}
