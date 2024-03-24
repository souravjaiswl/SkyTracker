package com.example.skytracker.model

import com.google.gson.annotations.SerializedName

data class Temperatures(
    @SerializedName("Metric")
    val metric: Metric,
    @SerializedName("Imperial")
    val imperial: Imperial
)
data class Metric(
    @SerializedName("Value")
    val value: Double,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int,
)
data class Imperial(
    @SerializedName("Value")
    val value: Int,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int,
)