package com.udacity.asteroidradar

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.AsteroidGridAdapter
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay

@BindingAdapter("dataList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context

    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("imageOfDay")
fun bindImageOfDay(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    val url = pictureOfDay?.url
    if (url != null) {
        val uri = Uri.parse(url)

        Picasso
            .with(imageView.context)
            .load(uri)
            .into(imageView)
    }
}

@BindingAdapter("contentDescriptionPicture")
fun bindContentDescriptionPicture(imageView: ImageView, string: String?) {
    val context = imageView.context
    val description =
        if (imageView.drawable == null) {
            String.format(
                context.getString(R.string.nasa_picture_of_day_content_description_format),
                string
            )
        } else {
            context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
        }
    imageView.contentDescription = description
}

@BindingAdapter("dateText")
fun bindTextViewToDateText(textView: TextView, string: String) {
    val context = textView.context
    textView.text = context.getString(R.string.asteroid_date_label) + " " + string

}

@BindingAdapter("distanceText")
fun bindTextViewToDistanceText(textView: TextView, number: Double) {
    val context = textView.context
    val distance = String.format(context.getString(R.string.astronomical_unit_round_format), number)
    textView.text = context.getString(R.string.asteroid_distance_label) + " " + distance
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
