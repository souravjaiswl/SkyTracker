package com.example.skytracker.skytracker


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skytracker.model.BaseModel
import com.example.skytracker.model.Location
import com.example.skytracker.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class searchTrackerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _locations = MutableStateFlow<BaseModel<List<Location>>?>(null)
    val location = _locations.asStateFlow()
    fun searchLocation(query: String) {
        viewModelScope.launch {
            _locations.update { BaseModel.Loading }
            val res = repository.searchLocation(query)
            _locations.update { res }
        }
    }
}