package com.example.skytracker.useCase

import com.example.skytracker.di.daoCityImplement
import com.example.skytracker.model.City
import javax.inject.Inject

class DeleteCity @Inject constructor(private val daoDeleteCity: daoCityImplement) {
    suspend operator fun invoke(city: City) = daoDeleteCity.deleteCity(city = city)
}