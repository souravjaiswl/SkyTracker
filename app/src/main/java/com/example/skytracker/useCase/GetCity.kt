package com.example.skytracker.useCase

import com.example.skytracker.di.daoCityImplement
import javax.inject.Inject

class GetCity @Inject constructor(private val daoGetCity: daoCityImplement) {
    operator fun invoke()= daoGetCity.getCity()
}