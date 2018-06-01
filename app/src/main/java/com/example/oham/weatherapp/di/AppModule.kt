package com.example.oham.weatherapp.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var application: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context
    {
        return application;
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient{
        return FusedLocationProviderClient(context)
    }

}