package com.example.oham.weatherapp.data.remote

import com.example.oham.weatherapp.BuildConfig
import com.example.oham.weatherapp.data.entities.WeatherInfo
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        val BASE_URL = BuildConfig.OPEN_WEATHER_BASE_URL
    }

    @GET("/data/2.5/weather")
    fun getWeather(@Query("zip") zipCode:String?,
                   @Query("APPID") appId:String = BuildConfig.OPEN_WEATHER_API_KEY,
                   @Query("units") units:String = "metric")
            : Observable<Response<WeatherInfo>>

}