package com.udacity.asteroidradar.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.DatabasePictureOfDay
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
) : Parcelable {

    fun toDatabasePictureOfDay(): DatabasePictureOfDay =
        DatabasePictureOfDay(
            url = url,
            mediaType = mediaType,
            title = title
        )
}