package com.sakr.prayertimesapp.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sakr.prayertimesapp.domain.models.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): GenericApiResponse<T> {

    return withContext(Dispatchers.IO) {
        try {
            val response: Response<T> = apiToBeCalled()
            if (response.isSuccessful) {
                Log.e("TAG", "safeApiCall: isSuccessful")
                GenericApiResponse.Success(data = response.body()!!)
            } else {
                Log.e("TAG", "safeApiCall: error")

                val gson = Gson()
                val type = object : TypeToken<ErrorResponse>() {}.type
                val errorResponse: ErrorResponse? =
                    gson.fromJson(response.errorBody()!!.charStream(), type)
                GenericApiResponse.Error(
                    errorMessage =
                    errorResponse?.data
                        ?: "Something went wrong"
                )

            }

        } catch (e: HttpException) {
            Log.e("TAG", "safeApiCall: HttpException")
            GenericApiResponse.Error(errorMessage = e.message ?: "Something went wrong")
        } catch (e: IOException) {
            Log.e("TAG", "safeApiCall: IOException")
            GenericApiResponse.Error("Please check your network connection")
        } catch (e: Exception) {
            Log.e("TAG", "safeApiCall: Exception")
            GenericApiResponse.Error(errorMessage = "Something went wrong")
        }
    }
}