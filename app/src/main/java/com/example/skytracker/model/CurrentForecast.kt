package com.example.skytracker.model

import com.google.gson.annotations.SerializedName

data class CurrentForecast(
    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String,
    @SerializedName("EpochTime")
    val epochTime: Int,
    @SerializedName("WeatherText")
    val weatherText: String,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType")
    val precipitationType: Any,
    @SerializedName("IsDayTime")
    val isDayTime: Boolean,
    @SerializedName("Temperature")
    val temperature: Temperatures,
)//Creator - SouravJaiswal