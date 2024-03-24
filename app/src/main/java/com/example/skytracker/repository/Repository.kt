package com.example.skytracker.repository

import com.example.skytracker.api.ApiInterface
import com.example.skytracker.model.BaseModel
import com.example.skytracker.model.CurrentForecast
import com.example.skytracker.model.DailyForecasts
import com.example.skytracker.model.HourlyForecast
import com.example.skytracker.model.Location
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun searchLocation(query: String): BaseModel<List<Location>> {
        return request {
            apiInterface.searchLocation(query = query)
        }
    }
    //Creator - SouravJaiswal
    suspend fun getDailyForecasts(locationKey: String): BaseModel<DailyForecasts> {
        return request {
            apiInterface.getDailyForecasts(locationKey = locationKey)
        }
    }

    suspend fun getHourlyForecasts(locationKey: String): BaseModel<List<HourlyForecast>> {
        return request {
            apiInterface.getHourlyForecasts(locationKey = locationKey)
        }
    }

    suspend fun getCurrentForecast(locationKey: String): BaseModel<ArrayList<CurrentForecast>> {
        return request {
            apiInterface.getCurrentForecast(locationKey = locationKey)
        }
    }
}
//Creator - SouravJaiswal
suspend fun <T> request(request: suspend () -> Response<T>): BaseModel<T> {
    try {
        request().also {
            return if (it.isSuccessful) {
                BaseModel.Success(it.body()!!)
            } else {
                BaseModel.Error(it.errorBody()?.string().toString())
            }
        }
    } catch (e: Exception) {
        return BaseModel.Error(e.message.toString())
    }
}