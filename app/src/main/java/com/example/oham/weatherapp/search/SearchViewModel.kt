package com.example.oham.weatherapp.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.example.oham.weatherapp.data.LocationRepository
import com.example.oham.weatherapp.data.Resource
import com.example.oham.weatherapp.data.Status
import com.example.oham.weatherapp.data.WeatherRepository
import com.example.oham.weatherapp.data.entities.WeatherInfo
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable



class SearchViewModel @Inject constructor(val weatherRepository: WeatherRepository, val locationRepository: LocationRepository): ViewModel(){


    var compositeDisposable = CompositeDisposable()

    companion object {
        private val tag = "${SearchViewModel::javaClass}_tag_"
    }

    init {
        compositeDisposable.add(weatherRepository.weatherResource.subscribe({resource -> onNext(resource)}))
        compositeDisposable.add(locationRepository.locationResource.subscribe({println("====>Location status: ${it.status} message: ${it.message} data: ${it.data}")}))

        locationRepository.retrieveLastLocation()
    }

    private fun onNext(resource: Resource<WeatherInfo>){
        result.value = resource
    }

    val query: MutableLiveData<String?> = MutableLiveData()
    val result: MutableLiveData<Resource<WeatherInfo>> = MutableLiveData()


    fun dosearch(zipCode: String?){
        if(zipCode == null){
            return
        }

        // TODO: Validate zipcode format

        if(result.value?.status == Status.ERROR || zipCode != this.query.value){
            this.query.value = zipCode
            weatherRepository.getWeather(zipCode!!)
        }
    }

    // TODO: Improve this function
    fun forceSync(){
        if(this.query.value != null){
            weatherRepository.getWeather(this.query.value!!)
        } else {
            result.value = Resource.success(null);
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}