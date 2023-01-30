package com.example.weatherforecastassignment.api

import com.example.weatherforecastassignment.model.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getWeatherOnCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Call<WeatherData>
}