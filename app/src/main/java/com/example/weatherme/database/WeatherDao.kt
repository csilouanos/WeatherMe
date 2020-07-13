package com.example.weatherme.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    suspend fun getAll(): List<Weather>

    @Query("SELECT * FROM weather WHERE name=:cityName")
    suspend fun findByCityName(cityName: String): List<Weather>

    @Query("SELECT * FROM weather WHERE LOWER(name) LIKE '%' || LOWER(:cityName) || '%' AND is_saved=0")
    suspend fun findNonSavedCities(cityName: String): List<Weather>

    @Query("SELECT * FROM weather WHERE is_saved=1")
    suspend fun findAllSavedCities(): List<Weather>

    @Query("UPDATE weather SET is_saved=1 WHERE name = :cityName")
    suspend fun setSaved(cityName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @Update
    suspend fun updateWeather(weather: Weather)

    suspend fun insertOrUpdate(weather: Weather) {
        val entries = findByCityName(weather.name)
        if(entries.isEmpty()) {
            insertWeather(weather)
        } else {
            updateWeather(weather)
        }
    }
}