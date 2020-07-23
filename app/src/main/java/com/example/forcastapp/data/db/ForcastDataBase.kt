package com.example.forcastapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forcastapp.data.db.entity.Converter
import com.example.forcastapp.data.db.entity.CurrentWeatherEntery

@TypeConverters(Converter::class)
@Database(entities = [CurrentWeatherEntery::class],version = 1)
abstract class ForcastDataBase:RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    companion object{
        @Volatile private var instance:ForcastDataBase? =null
        private val LOCK = Any()

        operator fun invoke(context:Context)= instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also { instance=it }
        }

        private fun buildDatabase(context:Context)=
            Room.databaseBuilder(context.applicationContext,ForcastDataBase::class.java,"forecast.db").build()
    }
}