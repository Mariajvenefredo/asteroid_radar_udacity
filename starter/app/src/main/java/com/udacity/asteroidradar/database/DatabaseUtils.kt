package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid

fun List<Asteroid>.asDatabaseAsteroids(): Array<DatabaseAsteroid> {
    val asteroidsDatabase = this.map { asteroid -> asteroid.toDatabaseAsteroid() }
    return asteroidsDatabase.toTypedArray()
}

fun LiveData<List<DatabaseAsteroid>>.asAsteroids(): LiveData<List<Asteroid>> {
    val asteroids = Transformations.map(this) {
        it.map { databaseAsteroid -> databaseAsteroid.toAsteroid() }
    }
    return asteroids
}