package com.example.skytracker.skytracker

import androidx.lifecycle.ViewModel
import com.example.skytracker.useCase.CityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetDataViewModel @Inject constructor(private val useCase: CityUseCase): ViewModel() {
    val getCity = useCase.getCity()
}