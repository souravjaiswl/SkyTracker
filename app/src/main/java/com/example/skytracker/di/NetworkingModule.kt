package com.example.skytracker.di

import android.content.Context
import androidx.room.Room
import com.example.skytracker.api.ApiInterface
import com.example.skytracker.useCase.CityUseCase
import com.example.skytracker.useCase.DeleteCity
import com.example.skytracker.useCase.GetCity
import com.example.skytracker.useCase.InsertCity
import com.example.skytracker.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Provides
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }//Creator - SouravJaiswal
    @Provides
    fun provideService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    //CityModule
    @Provides//Creator - SouravJaiswal
    fun provideDataBase(@ApplicationContext context: Context):  CityDatabase = Room.databaseBuilder(
        context = context,
        klass = CityDatabase::class.java,
        name = "CityDB"
    ).build()

    @Provides
    fun provideDao(dao: CityDatabase)= dao.city()

    @Provides
    fun provideCityUseCase(daoCityImplement: daoCityImplement) = CityUseCase(
        insertCity = InsertCity(daoCityImplement),
        deleteCity = DeleteCity(daoCityImplement),
        getCity = GetCity(daoCityImplement)
    )
}