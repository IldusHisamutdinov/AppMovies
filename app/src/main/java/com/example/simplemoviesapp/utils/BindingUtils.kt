package com.example.simplemoviesapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simplemoviesapp.R

@BindingAdapter("posterResource")
fun setPosterResource(poster: ImageView, poster_path: String?) {
    val url = "https://image.tmdb.org/t/p/w342$poster_path"

    val context = poster.context

    val options = RequestOptions()
        .error(R.drawable.ic_launcher_background)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(poster)
}