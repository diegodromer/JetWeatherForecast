package com.bawp.jetweatherforecast.repository

import com.bawp.jetweatherforecast.data.WeatherDao
import com.bawp.jetweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun removeAllFavorites() = weatherDao.removeAllFavorites()
    suspend fun removeFavorite(favorite: Favorite) = weatherDao.removeFavorite(favorite)
    suspend fun getFavById(city: String) = weatherDao.getFavById(city)
}