package com.example.forcastapp.data.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromListToString(list: List<String>): String? {
        return list.get(0)
    }

    @TypeConverter
    fun fromStringToList(s:String): List<String> {
        val list = listOf(s)
        return list
    }

}