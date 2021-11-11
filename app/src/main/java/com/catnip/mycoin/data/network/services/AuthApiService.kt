package com.catnip.mycoin.data.network.services

import com.catnip.mycoin.BuildConfig
import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface AuthApiService {
    @POST("auth/login")
    suspend fun postLoginUser(@Body loginRequest: AuthRequest): BaseAuthResponse<UserData, String>

    @POST("auth/register")
    suspend fun postRegisterUser(@Body registerRequest: AuthRequest): BaseAuthResponse<UserData, String>

    @GET("auth/me")
    suspend fun getSyncUser(): BaseAuthResponse<UserData, String>

    @GET("users")
    suspend fun getUserData(): BaseAuthResponse<UserData, String>

    @PUT("users")
    suspend fun putUserData(@Body data: RequestBody): BaseAuthResponse<UserData, String>


    companion object {
        @JvmStatic
        operator fun invoke(localDataSource: LocalDataSource) : AuthApiService {
            val authInterceptor = Interceptor {
                val requestBuilder = it.request().newBuilder()
                localDataSource.getAuthToken()?.let { token ->
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                it.proceed(requestBuilder.build())
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_BINAR_AUTH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(AuthApiService::class.java)
        }
    }
}