package com.example.oham.weatherapp.data

import android.location.Location
import com.example.oham.weatherapp.LocationManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(val locationManager: LocationManager){

    var lastLocation: Location? = null

    val locationResource: BehaviorSubject<Resource<Location>> = BehaviorSubject.create()

    fun retrieveLastLocation(){
        locationResource.onNext(Resource.loading(lastLocation))
        locationManager.lastLocation
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({next -> onNext(next)},{ error -> onError(error)} )

        locationManager.getLastLocation()

    }

    fun onNext(resLocation: Resource<Location>){
        when(resLocation.status){
            Status.SUCCESS -> locationResource.onNext(Resource.success(resLocation.data))
            Status.ERROR -> locationResource.onNext(Resource.error(resLocation.message!!, lastLocation))
        }
        lastLocation = resLocation.data
    }

    fun onError(throwable: Throwable){
        locationResource.onNext(Resource.error("Error trying to retrieve last location", lastLocation))
    }

}