package com.example.skytracker.skytracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skytracker.model.BaseModel
import com.example.skytracker.model.CurrentForecast
import com.example.skytracker.model.DailyForecasts
import com.example.skytracker.model.HourlyForecast
import com.example.skytracker.model.Location
import com.example.skytracker.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class skyTrackerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _hourlyForecast = MutableStateFlow<BaseModel<List<HourlyForecast>>?>(null)//Creator - SouravJaiswal
    val hourlyForecast = _hourlyForecast.asStateFlow()

    fun getHourlyForecast(locationKey: String) {
        viewModelScope.launch {
            _hourlyForecast.update { BaseModel.Loading }
            val res = repository.getHourlyForecasts(locationKey)
            _hourlyForecast.update { res }
        }
    }

    private val _dailyForecast = MutableStateFlow<BaseModel<DailyForecasts>?>(null)
    val dailyForecast = _dailyForecast.asStateFlow()

    fun getDailyForecast(locationKey:String){
        viewModelScope.launch {
            _dailyForecast.update { BaseModel.Loading }
            val res = repository.getDailyForecasts(locationKey)
            _dailyForecast.update { res }
        }
    }

    private val _currentForecast = MutableStateFlow<BaseModel<ArrayList<CurrentForecast>>?>(null)
    val currentForecast = _currentForecast.asStateFlow()

    fun getCurrentForecast(locationKey:String){
        viewModelScope.launch {
            _currentForecast.update { BaseModel.Loading }
            val res = repository.getCurrentForecast(locationKey)
            _currentForecast.update { res }
        }
    }
}