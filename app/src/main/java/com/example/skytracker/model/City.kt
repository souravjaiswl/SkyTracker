package com.example.skytracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
//Creator - SouravJaiswal
//Entities(Table)
@Entity(tableName =  "city")
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val locationKey: String = "",
    val locationName: String = "",
    val locationCountryName: String = "",

)
