package com.example.forcastapp.ui.weather.current

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.forcastapp.R
import com.example.forcastapp.data.network.ApixuWeatherApi
import com.example.forcastapp.data.network.ConnectivityInterceptor
import com.example.forcastapp.data.network.ConnectivityInterceptorImpl
import com.example.forcastapp.data.network.WeatherNetworkDataSourceImpl
import com.example.forcastapp.data.network.response.CurrentWeatherResponse
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
        val apiService = ApixuWeatherApi(ConnectivityInterceptorImpl(this.requireContext()))
        val weatherNetworkDataSource= WeatherNetworkDataSourceImpl(apiService)
        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner, Observer {
            Log.i("currentWetherResponse:","${it.currentWeatherEntery}")
            text_view.text=it.currentWeatherEntery.toString()
        })
        GlobalScope.launch(Dispatchers.Main) {
                weatherNetworkDataSource.fetchCurrentWeather("New York")
        }
    }

}
