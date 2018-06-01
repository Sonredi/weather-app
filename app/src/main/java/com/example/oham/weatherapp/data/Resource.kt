package com.example.oham.weatherapp.data

import android.support.annotation.NonNull
import android.support.annotation.Nullable

class Resource<T> private constructor(
        val status: Status,
        val data: T?,
        val message: String?) {

    companion object {

        fun <T> success(@NonNull data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, @Nullable data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(@Nullable data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}