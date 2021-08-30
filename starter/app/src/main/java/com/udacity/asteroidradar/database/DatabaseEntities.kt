package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay

@Entity
data class DatabaseAsteroid constructor(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) {
    fun toAsteroid(): Asteroid =
        Asteroid(
            id = id,
            codename = codename,
            closeApproachDate = closeApproachDate,
            absoluteMagnitude = absoluteMagnitude,
            estimatedDiameter = estimatedDiameter,
            relativeVelocity = relativeVelocity,
            distanceFromEarth = distanceFromEarth,
            isPotentiallyHazardous = isPotentiallyHazardous
        )
}

@Entity
data class DatabasePictureOfDay constructor(
    @PrimaryKey val url: String,
    val mediaType: String,
    val title: String
) {
    fun toPictureOfDay(): PictureOfDay =
        PictureOfDay(
            url = url,
            mediaType = mediaType,
            title = title
        )
}
