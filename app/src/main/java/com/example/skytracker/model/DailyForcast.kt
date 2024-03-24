package com.example.skytracker.model

import com.google.gson.annotations.SerializedName

data class DailyForecast(
    @SerializedName("Date")
    val date: String,
    @SerializedName("EpochDate")
    val epochDate: Long,
    @SerializedName("Temperature")
    val temperature: Temperature,
    @SerializedName("Day")
    val day: WeatherState,
    @SerializedName("Night")
    val night: WeatherState
)//Creator - SouravJaiswal

data class Temperature(
    @SerializedName("Minimum")
    val min: Value,
    @SerializedName("Maximum")
    val max: Value
)

data class WeatherState(
    @SerializedName("Icon")
    val icon: Int,
    @SerializedName("IconPhrase")
    val iconPhrase: String,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean
)
