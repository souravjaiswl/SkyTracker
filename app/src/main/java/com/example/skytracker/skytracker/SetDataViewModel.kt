package com.example.skytracker.skytracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skytracker.model.City
import com.example.skytracker.useCase.CityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetDataViewModel @Inject constructor(private val useCase: CityUseCase): ViewModel() {
    val citys = useCase.getCity()

    fun deleteCity(city: City) = viewModelScope.launch {
        useCase.deleteCity(city)
    }
    // Database
    var key by mutableStateOf("")
    var name by mutableStateOf("")
    var countryName by mutableStateOf("")

    fun addCity() = viewModelScope.launch {
        useCase.insertCity(city = City(locationKey = key, locationName = name, locationCountryName = countryName))
    }
}