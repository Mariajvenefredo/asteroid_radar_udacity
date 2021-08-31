package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(vararg asteroids: DatabaseAsteroid)

    @Query("select * from DatabaseAsteroid WHERE closeApproachDate >= :today order by closeApproachDate desc")
    fun getAllAsteroids(today: String): LiveData<List<DatabaseAsteroid>>

    @Query("select * from DatabaseAsteroid WHERE closeApproachDate = :date ORDER BY closeApproachDate DESC")
    fun getTodayAsteroids(date: String): LiveData<List<DatabaseAsteroid>>

    @Query("select * from DatabaseAsteroid WHERE closeApproachDate BETWEEN :startDate AND :endDate")
    fun getWeekAsteroids(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>

    @Query("delete from DatabaseAsteroid")
    fun deleteAllAsteroids()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfDay(vararg pictureOfDay: DatabasePictureOfDay)

    @Query("SELECT * FROM DatabasePictureOfDay")
    fun getPictureOfDay(): DatabasePictureOfDay
}