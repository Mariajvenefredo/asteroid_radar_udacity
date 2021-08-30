package com.udacity.asteroidradar.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.AsteroidFilter
import com.udacity.asteroidradar.api.parseAsteroidsStringResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asAsteroids
import com.udacity.asteroidradar.database.asDatabaseAsteroids
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class AsteroidRepository(private val database: AsteroidDatabase) {

    private val todayDate = LocalDate.now()

    private val allAsteroids = database.asteroidDao.getAllAsteroids(todayDate.toString())

    private val filter = MutableLiveData(AsteroidFilter.ALL)

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.switchMap(filter) {
            val endDate = todayDate.plusWeeks(1)
            when (filter.value) {
                AsteroidFilter.WEEK -> getWeekAsteroids(todayDate, endDate)
                AsteroidFilter.DAY -> getDayAsteroids(todayDate)
                else -> allAsteroids.asAsteroids()
            }
        }

    suspend fun addAsteroidsToDatabase() {
        withContext(Dispatchers.IO) {
            val asteroidApiList = AsteroidApi.retrofitService.getAsteroids(Constants.API_KEY)
            val asteroids = parseAsteroidsStringResult(asteroidApiList)
            database.asteroidDao.insertAllAsteroids(*asteroids.asDatabaseAsteroids())
        }
    }

    suspend fun addPictureOfDay(): PictureOfDay =
        withContext(Dispatchers.IO) {
            val picture =
                AsteroidApi.retrofitService.getPictureOfDay(Constants.API_KEY, todayDate)

            database.asteroidDao.insertPictureOfDay(picture.toDatabasePictureOfDay())
            return@withContext picture
        }


    fun updateAsteroids(asteroidFilter: AsteroidFilter) =
        filter.postValue(asteroidFilter)

    private fun getDayAsteroids(startDate: LocalDate) =
        database
            .asteroidDao
            .getTodayAsteroids(startDate.toString())
            .asAsteroids()

    private fun getWeekAsteroids(
        startDate: LocalDate,
        endDate: LocalDate
    ) =
        database
            .asteroidDao
            .getWeekAsteroids(startDate.toString(), endDate.toString())
            .asAsteroids()
}