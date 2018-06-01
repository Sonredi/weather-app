package com.example.oham.weatherapp.di

import com.example.oham.weatherapp.search.SearchActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, ViewModelModule::class))
interface AppComponent {
    fun inject(searchActivity: SearchActivity)
}