package com.example.weatherme.network

import com.example.weatherme.BuildConfig
import com.example.weatherme.constants.Keys
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkManager {

    private val retrofitService: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(generateOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val networkService: RestClientService by lazy {
        retrofitService.create(RestClientService::class.java)
    }

    companion object {
        val shared = NetworkManager()
    }

    private fun generateOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(generateLoggingInterceptor())
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url
                val url =
                    originalHttpUrl.newBuilder().addQueryParameter("key", Keys.API_KEY).build()
                request.url(url)
                return@addInterceptor chain.proceed(request.build())
            }
            .build()
    }

    private fun generateLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}

