package com.example.forcastapp.data.network

import android.util.Log
import com.example.forcastapp.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY ="b87f352a4bef66d3d6635c7666e2c6c8"

//URL = http://api.weatherstack.com/current?access_key=b87f352a4bef66d3d6635c7666e2c6c8&query=New%20York

interface ApixuWeatherApi {

    @GET("current")
    fun getCurrentWeather(
        @Query("access_key")apiKey:String= API_KEY,
        @Query("query")location:String
    ):Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke(connectivityInterceptor:ConnectivityInterceptor): ApixuWeatherApi {
                val requestInterceptor = Interceptor {chain ->
                    val url = chain.request()
                        .url()
                        .newBuilder()
//                        .addQueryParameter("access_key", API_KEY)
                        .build()
                    val request =chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    Log.i("currentWetherResponse","$url")
                    return@Interceptor chain.proceed(request)
                }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApi::class.java)
        }
    }

}