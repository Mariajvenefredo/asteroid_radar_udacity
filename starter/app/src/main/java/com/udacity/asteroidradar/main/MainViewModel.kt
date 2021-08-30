package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidFilter
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getInstance(application)
    private val asteroidRepository = AsteroidRepository(database)
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val asteroids = asteroidRepository.asteroids
    val pictureOfDay get() = _pictureOfDay

    init {
        viewModelScope.launch {
            asteroidRepository.addAsteroidsToDatabase()
            _pictureOfDay.value = asteroidRepository.addPictureOfDay()
        }
    }

    fun updateFilter(filter: AsteroidFilter) =
        viewModelScope.launch {
            asteroidRepository.updateAsteroids(filter)
        }
}