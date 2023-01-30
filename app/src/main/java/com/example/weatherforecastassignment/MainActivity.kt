package com.example.weatherforecastassignment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.weatherforecastassignment.api.RetrofitInstance
import com.example.weatherforecastassignment.databinding.ActivityMainBinding
import com.example.weatherforecastassignment.model.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchWeather()
    }

    private fun searchWeather() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    RetrofitInstance.weatherApi.getWeatherOnCity(query, WEATHER_API_ID).enqueue(object : Callback<WeatherData>{
                        @SuppressLint("SetTextI18n")
                        override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                            if (response.isSuccessful){
                                response.body()?.let {
                                    val tempResp = it.main.temp
                                    val sunriseResp = it.sys.sunrise.toLong()
                                    val sunsetResp = it.sys.sunset.toLong()

                                    val temp = tempResp.minus(273.15)

                                    binding.apply {
                                        txtCity.text = query
                                        txtTemp.text = "${temp.toInt()}°c"
                                        txtSunrise.text = convertLongToTime(sunriseResp)
                                        txtSunset.text = convertLongToTime(sunsetResp)
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "$t", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun convertLongToTime(timeStamp: Long): String{
        val time = Instant.ofEpochSecond(timeStamp)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
        return time.toString()
    }
}