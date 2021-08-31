package com.udacity.asteroidradar.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
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


    suspend fun addAsteroidsToDatabase() {
        withContext(Dispatchers.IO) {
            val asteroidApiList = AsteroidApi.retrofitService.getAsteroids(Constants.API_KEY)
            val asteroids = parseAsteroidsStringResult(asteroidApiList)
            database.asteroidDao.insertAllAsteroids(*asteroids.asDatabaseAsteroids())
        }
    }

    suspend fun addPictureOfDay() =
        withContext(Dispatchers.IO) {
            val picture =
                AsteroidApi.retrofitService.getPictureOfDay(Constants.API_KEY, todayDate)

            database.asteroidDao.insertPictureOfDay(picture.toDatabasePictureOfDay())
        }

    fun getDayAsteroids(): LiveData<List<Asteroid>> =
        database
            .asteroidDao
            .getTodayAsteroids(todayDate.toString())
            .asAsteroids()

    fun getWeekAsteroids(): LiveData<List<Asteroid>> {
        val endDate = todayDate.plusWeeks(1)

        return database
            .asteroidDao
            .getWeekAsteroids(todayDate.toString(), endDate.toString())
            .asAsteroids()
    }

    fun getAllAsteroids(): LiveData<List<Asteroid>> = allAsteroids.asAsteroids()

    suspend fun getPictureOfDay(): PictureOfDay =
        withContext(Dispatchers.IO) {
            database.asteroidDao.getPictureOfDay().toPictureOfDay()
        }
}