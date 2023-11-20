package com.mateusz.jasiak.activetimespendingsystem.domain.repositories.base

import android.util.Log
import com.google.gson.stream.MalformedJsonException
import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ErrorResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.enums.ErrorCode
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.net.ConnectException

interface BaseRepository {
    companion object {
        private const val TAG_ERROR = "BaseRepositoryError"
        private const val TAG_DEBUG = "BaseRepositoryDebug"
    }

    suspend fun <T> handleRequest(call: suspend () -> Response<T>): ApiResponse<T> {
        return try {
            val apiResponse = call.invoke()
            when (apiResponse.isSuccessful) {
                true -> ApiResponse.Success(apiResponse.body())
                else -> handleFailureResponse(apiResponse.errorBody())
            }
        } catch (exception: Exception) {
            exception.localizedMessage?.let { Log.e(TAG_ERROR, it) }
            exception.printStackTrace()
            handleException(exception)
        }
    }

    fun <T> handleFailureResponse(response: ResponseBody?): ApiResponse<T> {
        response?.let {
            val jsonObject = JSONObject(it.string())
            try {
                val code = jsonObject.getInt("status")
                val error = jsonObject.getString("error")
                val errorMessage = jsonObject.getString("message")

                val errorCode: ErrorCode = when (code) {
                    401 -> ErrorCode.UNAUTHORIZED
                    404 -> ErrorCode.NOT_FOUND
                    else -> ErrorCode.UNKNOWN
                }

                return ApiResponse.Failure(ErrorResponse(errorCode, error, errorMessage))
            } catch (exception: Exception) {
                Log.d(
                    TAG_DEBUG,
                    exception.message ?: "Unknown error while handling failure response"
                )
            }
        }
        return ApiResponse.Failure(ErrorResponse(ErrorCode.UNKNOWN))
    }

    private fun <T> handleException(exception: Exception?): ApiResponse<T> {
        exception?.let {
            val errorResponse = when (exception) {
                is ConnectException -> {
                    ErrorResponse(ErrorCode.NO_NETWORK)
                }

                is MalformedJsonException -> {
                    ErrorResponse(ErrorCode.BAD_RESPONSE)
                }

                else -> {
                    ErrorResponse(ErrorCode.UNKNOWN)
                }
            }
            return ApiResponse.Failure(errorResponse)
        }
        return ApiResponse.Failure(ErrorResponse(ErrorCode.UNKNOWN))
    }
}