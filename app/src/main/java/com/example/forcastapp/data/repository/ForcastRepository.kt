package com.example.forcastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.forcastapp.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.example.forcastapp.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForcastRepository {
    suspend fun getCurrentWeather():LiveData<ImperialCurrentWeatherEntry>
}