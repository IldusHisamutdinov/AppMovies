package com.example.simplemoviesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.simplemoviesapp.models.RemoteMovieDetail
import com.example.simplemoviesapp.repositories.MoviesRepository

class MainViewModel : ViewModel() {
    private val mRepo = MoviesRepository
    val movies: LiveData<List<RemoteMovieDetail>> get() = mRepo.movies //getter
    private var upcomingMoviesPage = 0

    fun getUpcomingMovies(onError: () -> Unit) {
        upcomingMoviesPage++
        MoviesRepository.getUpcomingMovies(upcomingMoviesPage, onError)
    }
}