package com.example.oham.weatherapp

import android.app.Application
import com.example.oham.weatherapp.di.AppComponent
import com.example.oham.weatherapp.di.AppModule
import com.example.oham.weatherapp.di.DaggerAppComponent


class WeatherApp(): Application(){

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getAppComponent(): AppComponent? {
        return appComponent
    }
}