package com.example.forcastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.forcastapp.data.db.CurrentWeatherDao
import com.example.forcastapp.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.example.forcastapp.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.forcastapp.data.network.WeatherNetworkDataSource
import com.example.forcastapp.data.network.WeatherNetworkDataSourceImpl
import com.example.forcastapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForcastRepositoryImpl(
    private val currentWeatherDao:CurrentWeatherDao,
    private val weatherNetworkDataSource:WeatherNetworkDataSource
) : ForcastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather->
            persistFetchedCurrentWthaer(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(): LiveData<ImperialCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
                return@withContext currentWeatherDao.getWeatherData()
        }
    }

    private fun persistFetchedCurrentWthaer(fetchedWeather:CurrentWeatherResponse){
            GlobalScope.launch(Dispatchers.IO) {
                currentWeatherDao.upsert(fetchedWeather.currentWeatherEntery)
            }
    }
    private suspend fun initWeatherData(){
        if(isFetchedCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchCurrentWeather()
        }

    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather("New York")
    }

    private fun isFetchedCurrentNeeded(lastFetchedTime:ZonedDateTime):Boolean{
        val thirtyMinuteAgo =ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinuteAgo)
    }
}