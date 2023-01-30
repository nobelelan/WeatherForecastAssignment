package com.example.weatherforecastassignment.model

data class WeatherData(
    val coord: Coord,
    val id: Int,
    val main: Main,
    val sys: Sys
)