package com.example.skytracker.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.skytracker.model.City
import kotlinx.coroutines.flow.Flow

// Room Database - DAO
// * Data Access Object
// * Interface containing methods to access database - CURD Operations
// * We can define multiple DAOs

@Dao
interface DaoCity {
    @Insert
    suspend fun insertCity(city: City)
    @Delete
    suspend fun deleteCity(city: City)
    @Query("select * from city")
    fun getCity(): Flow<List<City>>
}