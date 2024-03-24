package com.example.skytracker.skytracker

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skytracker.model.BaseModel
import com.example.skytracker.model.CurrentForecast
import com.example.skytracker.model.DailyForecasts
import com.example.skytracker.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class detailsViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dailyForecast = MutableStateFlow<BaseModel<DailyForecasts>?>(null)
    val dailyForecast = _dailyForecast.asStateFlow()
    private val id: String? = savedStateHandle.get<String>(key = "id")

    fun getDailyForecast() {
        viewModelScope.launch {
            _dailyForecast.update { BaseModel.Loading }
            val res = id?.let { repository.getDailyForecasts(it) }
            _dailyForecast.update { res }
        }
    }

    private val _currentForecast = MutableStateFlow<BaseModel<ArrayList<CurrentForecast>>?>(null)
    val currentForecast = _currentForecast.asStateFlow()

    fun getCurrentForecast() {
        viewModelScope.launch {
            _currentForecast.update { BaseModel.Loading }
            val res = id?.let { repository.getCurrentForecast(it) }
            _currentForecast.update { res }
        }
    }
    init {
        viewModelScope.launch {
            getCurrentForecast()
            getDailyForecast()
        }
    }
}