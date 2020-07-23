package com.example.forcastapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forcastapp.data.db.entity.CURRENT_WEATHER_ID
import com.example.forcastapp.data.db.entity.CurrentWeatherEntery
import com.example.forcastapp.data.db.unitlocalized.ImperialCurrentWeatherEntry
@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert (WeatherEntry:CurrentWeatherEntery)
    @Query(value = "select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherData():LiveData<ImperialCurrentWeatherEntry>
}