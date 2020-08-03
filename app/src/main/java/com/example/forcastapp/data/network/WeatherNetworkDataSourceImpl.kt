package com.example.forcastapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forcastapp.data.network.response.CurrentWeatherResponse
import com.example.forcastapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl( private val apixuWeatherApi:ApixuWeatherApi) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeather = apixuWeatherApi.getCurrentWeather(location = location).await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e:NoConnectivityException){
            Log.i("connectivity", "No Internet Exception",e)
        }
    }
}