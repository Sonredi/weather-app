package com.example.oham.weatherapp.data.remote

import com.example.oham.weatherapp.data.entities.WeatherInfo
import io.reactivex.Observable
import retrofit2.Response

class RemoteDataSource(var weatherService: WeatherService){

    fun getWeather(zipCode: String): Observable<Response<WeatherInfo>>? {
        return weatherService.getWeather(zipCode)
    }

}