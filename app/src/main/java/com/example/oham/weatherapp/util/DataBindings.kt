package com.example.oham.weatherapp.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.oham.weatherapp.R
import java.text.SimpleDateFormat
import java.util.Date

object DataBindings {

    val simpleDateFormat = SimpleDateFormat()

    @JvmStatic
    @BindingAdapter("dateTime")
    fun setDateTime(textView: TextView, dateTime: Date){
        if(dateTime != null){
            textView.text = simpleDateFormat.format(dateTime)
        }
    }


    @JvmStatic
    @BindingAdapter("weatherIcon")
    fun setWeatherIcon(imageView: ImageView, iconId: String?){
        if(iconId != null){
            when(iconId){
                "01d" -> imageView.setBackgroundResource(R.drawable.ic_weather_clear_sky_day)
                "01n" -> imageView.setBackgroundResource(R.drawable.ic_weather_clear_sky_night)

                "02d" -> imageView.setBackgroundResource(R.drawable.ic_weather_few_clouds_day)
                "02n" -> imageView.setBackgroundResource(R.drawable.ic_weather_few_clouds_night)

                "03d",
                "03n" -> imageView.setBackgroundResource(R.drawable.ic_weather_scattered_clouds)

                "04d",
                "04n" -> imageView.setBackgroundResource(R.drawable.ic_weather_broken_clouds)

                "09d",
                "09n" -> imageView.setBackgroundResource(R.drawable.ic_weather_shower_rain)

                "10d",
                "10n" -> imageView.setBackgroundResource(R.drawable.ic_weather_rain)

                "11d",
                "11n" -> imageView.setBackgroundResource(R.drawable.ic_weather_thunderstorm)

                "13d",
                "13n" -> imageView.setBackgroundResource(R.drawable.ic_weather_snowing)

                "50d" -> imageView.setBackgroundResource(R.drawable.ic_weather_mist_day)
                "50n" -> imageView.setBackgroundResource(R.drawable.ic_weather_mist_night)

                else -> imageView.setBackgroundResource(R.drawable.ic_globe)

            }
        }
    }


}