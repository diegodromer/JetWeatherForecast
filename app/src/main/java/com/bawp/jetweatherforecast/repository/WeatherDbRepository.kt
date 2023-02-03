package com.bawp.jetweatherforecast.repository

import com.bawp.jetweatherforecast.data.WeatherDao
import com.bawp.jetweatherforecast.model.Favorite
import com.bawp.jetweatherforecast.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun removeAllFavorites() = weatherDao.removeAllFavorites()
    suspend fun removeFavorite(favorite: Favorite) = weatherDao.removeFavorite(favorite)
    suspend fun getFavById(city: String) = weatherDao.getFavById(city)

    // Unit database
    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun removeAllUnit() = weatherDao.removeAllUnits()
    suspend fun removeUnit(unit: Unit) = weatherDao.removeUnit(unit)
}