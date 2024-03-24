package com.example.skytracker.api

import com.example.skytracker.model.CurrentForecast
import com.example.skytracker.model.DailyForecasts
import com.example.skytracker.model.HourlyForecast
import com.example.skytracker.model.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val APIKEY = "stUG0EuwrNlrW6zgxdpFHPiurru25aWE"
//Creator - SouravJaiswal
interface ApiInterface {
    @GET("locations/v1/cities/search")
    suspend fun searchLocation(
        @Query("apikey") apiKey: String = APIKEY,
        @Query("q") query: String
    ): Response<List<Location>>

    @GET("forecasts/v1/daily/5day/{location_key}")
    suspend fun getDailyForecasts(
        @Path("location_key") locationKey: String,
        @Query("apikey") apiKey: String = APIKEY,
        @Query("metric") metric: Boolean = true
    ): Response<DailyForecasts>

    @GET("forecasts/v1/hourly/12hour/{location_key}")
    suspend fun getHourlyForecasts(
        @Path("location_key") locationKey: String,
        @Query("apikey") apiKey: String = APIKEY,
        @Query("metric") metric: Boolean = true
    ): Response<List<HourlyForecast>>

    @GET("currentconditions/v1/{location_key}")
    suspend fun getCurrentForecast(
        @Path("location_key") locationKey: String,
        @Query("apikey") apiKey: String = APIKEY,
    ): Response<ArrayList<CurrentForecast>>
}