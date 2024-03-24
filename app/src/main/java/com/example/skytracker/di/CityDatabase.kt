package com.example.skytracker.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skytracker.model.City
import com.example.skytracker.repository.DaoCity
//Creator - SouravJaiswal
@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CityDatabase: RoomDatabase() {
    abstract fun city(): DaoCity
}