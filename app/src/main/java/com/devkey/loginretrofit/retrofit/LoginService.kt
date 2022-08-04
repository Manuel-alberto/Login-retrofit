package com.devkey.loginretrofit.retrofit

import com.devkey.loginretrofit.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")

    @POST(Constants.API_PATH + Constants.LOGIN_PATH)
    fun login(@Body data: UserInfo): Call<LoginResponse>

    @POST(Constants.API_PATH + Constants.LOGIN_PATH)
    suspend fun loginUser(@Body data: UserInfo): LoginResponse

    @POST(Constants.API_PATH + Constants.REGISTER_PATH)
    suspend fun registerUser(@Body data: UserInfo) : RegisterResponse

}