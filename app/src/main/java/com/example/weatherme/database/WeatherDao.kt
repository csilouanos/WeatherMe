package com.example.weatherme.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    suspend fun getAll(): List<Weather>

    @Query("SELECT * FROM weather WHERE name=:cityName")
    suspend fun findByCityName(cityName: String): List<Weather>

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert
    suspend fun insertWeather(weather: Weather)

    @Update
    suspend fun updateWeather(weather: Weather)
}