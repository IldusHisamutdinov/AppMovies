package com.example.simplemoviesapp.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simplemoviesapp.R
import com.example.simplemoviesapp.databinding.ActivityMainBinding
import com.example.simplemoviesapp.details.viewmodel.MovieDetailsViewModel
import com.squareup.picasso.Picasso

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

class MovieDetailsActivity : AppCompatActivity() {
    private val movieDetailsViewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movieId = intent.getStringExtra("movieId")
        if (movieId != null) {
            movieDetailsViewModel.sendServerRequestDate(movieId)
        }

        movieDetailsViewModel.getDataRequest().observe(this, Observer<Resource> {
            renderData(it)
        })
    }


    @SuppressLint("CutPasteId")
    private fun renderData(data: Resource) {
        when (data) {
            is Resource.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.posterPath
                val detailPoster: ImageView = findViewById(R.id.detail_poster)
                val detailTitle: TextView = findViewById(R.id.detail_title)
                val detailOverview: TextView = findViewById(R.id.detail_overview)
                val releaseDate: TextView = findViewById(R.id.detail_release_date)
                val popularity: TextView = findViewById(R.id.popularity)
                detailTitle.text = serverResponseData.title
                detailOverview.text = serverResponseData.overview
                releaseDate.text = serverResponseData.releaseDate
                popularity.text = serverResponseData.popularity
                Picasso.with(this)
                    .load(POSTER_BASE_URL + url)
                    .placeholder(R.drawable.divider)
                    .error(R.drawable.ic_star_border_black_24dp)
                    .into(detailPoster)
            }
            is Resource.Loading -> {
                Log.d("myLOG", "renderData Loading: ${data.progress}")
            }
            is Resource.Error -> {
                Log.d("myLOG", "renderData Error: ${data.error}")
            }
        }
    }

}