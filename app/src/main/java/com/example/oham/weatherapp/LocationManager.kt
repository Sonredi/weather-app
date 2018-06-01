package com.example.oham.weatherapp



import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import com.example.oham.weatherapp.data.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationManager @Inject constructor(val fusedLocationProvider: FusedLocationProviderClient, val context: Context): LocationCallback(){

    val lastLocation = BehaviorSubject.create<Resource<Location>>()

    fun getLastLocation(){
        val permission = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val res = context.checkCallingOrSelfPermission(permission)
        if (res == PackageManager.PERMISSION_GRANTED){
            fusedLocationProvider.lastLocation
                    .addOnSuccessListener { location ->
                        lastLocation.onNext(Resource.success(location))
                    }
                    .addOnCompleteListener({it -> println("FuseLocator service completed")})
        }else{
            lastLocation.onNext(Resource.error("Permissions not granted", null))
        }
    }


    fun startLocationUpdates(){
        val permission = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val res = context.checkCallingOrSelfPermission(permission)
        if (res == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProvider.requestLocationUpdates(createLocationRequest(), this, null)
        }
    }

    fun createLocationRequest():LocationRequest {
        return LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

}