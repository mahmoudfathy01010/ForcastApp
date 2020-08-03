package com.example.forcastapp

import android.app.Application
import com.example.forcastapp.data.db.ForcastDataBase
import com.example.forcastapp.data.network.*
import com.example.forcastapp.data.repository.ForcastRepository
import com.example.forcastapp.data.repository.ForcastRepositoryImpl
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForcastApplication: Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@ForcastApplication))
        bind() from singleton { ForcastDataBase(instance()) }
        bind() from singleton { instance<ForcastDataBase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton {ApixuWeatherApi(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForcastRepository>() with singleton { ForcastRepositoryImpl(instance(),instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}