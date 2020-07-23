package com.example.forcastapp.data.network.response


import com.example.forcastapp.data.db.entity.CurrentWeatherEntery
import com.example.forcastapp.data.db.entity.Location
import com.example.forcastapp.data.db.entity.Request
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntery: CurrentWeatherEntery,
    val location: Location,
    val request: Request
)