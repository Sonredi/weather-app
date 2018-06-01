package com.example.oham.weatherapp.data

import com.example.oham.weatherapp.data.entities.WeatherInfo
import com.example.oham.weatherapp.data.remote.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(val weatherService: WeatherService) {

    private var lastWeather: WeatherInfo? =null

    val weatherResource: BehaviorSubject<Resource<WeatherInfo>> = BehaviorSubject.create()

    fun getWeather(zipcode: String){
        weatherResource.onNext(Resource.loading(lastWeather))

        weatherService.getWeather(zipcode)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> onNext(response) }, { error -> onError(error) })
    }

    private fun onNext(response: Response<WeatherInfo>){
        when(response?.code()){
            200 -> {
                lastWeather = response.body()
                weatherResource.onNext(Resource.success(lastWeather!!))}
        // Todo: Implement a better error handler
            else -> weatherResource.onNext(Resource.error("Error trying to fetch data",lastWeather))
        }
    }

    private fun onError(throwable: Throwable){
        // Todo: Implement a better error handler
        weatherResource.onNext(Resource.error("Internal error",lastWeather))
    }
}