package com.example.skytracker.di

import com.example.skytracker.model.City
import com.example.skytracker.repository.DaoCity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class daoCityImplement @Inject constructor(private val dao: DaoCity) : DaoCity {
    override suspend fun insertCity(city: City) = dao.insertCity(city = city)
    //Creator - SouravJaiswal
    override suspend fun deleteCity(city: City) = dao.deleteCity(city = city)

    override fun getCity(): Flow<List<City>> = dao.getCity()
}