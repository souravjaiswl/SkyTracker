package com.example.skytracker.useCase

import com.example.skytracker.di.daoCityImplement
import com.example.skytracker.model.City
import javax.inject.Inject

class InsertCity @Inject constructor(private val daoInsertCity: daoCityImplement) {
    suspend operator fun invoke(city: City)= daoInsertCity.insertCity(city = city)
}