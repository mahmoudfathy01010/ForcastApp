package com.example.forcastapp.data.network

import androidx.lifecycle.LiveData
import com.example.forcastapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    suspend fun fetchCurrentWeather(location:String)
}