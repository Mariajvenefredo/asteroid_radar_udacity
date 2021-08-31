package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidFilter
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getInstance(application)
    private val asteroidRepository = AsteroidRepository(database)
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    private val filter = MutableLiveData(AsteroidFilter.ALL)
    val pictureOfDay get() = _pictureOfDay

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.switchMap(filter) {
            when (filter.value) {
                AsteroidFilter.WEEK -> asteroidRepository.getWeekAsteroids()
                AsteroidFilter.DAY -> asteroidRepository.getDayAsteroids()
                else -> asteroidRepository.getAllAsteroids()
            }
        }

    init {
        viewModelScope.launch {
            asteroidRepository.addAsteroidsToDatabase()
            asteroidRepository.addPictureOfDay()
            _pictureOfDay.value = asteroidRepository.getPictureOfDay()
        }
    }

    fun updateFilter(asteroidFilter: AsteroidFilter) =
        viewModelScope.launch {
            filter.postValue(asteroidFilter)
        }
}