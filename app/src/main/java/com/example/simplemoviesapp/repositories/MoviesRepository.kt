package com.example.simplemoviesapp.repositories

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplemoviesapp.BuildConfig
import com.example.simplemoviesapp.api.MovieService
import com.example.simplemoviesapp.models.GetMoviesResponse
import com.example.simplemoviesapp.models.RemoteMovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Singleton
object MoviesRepository {
    private val api: MovieService
    private var mMoviesList: List<RemoteMovieDetail> = listOf()
    private val mMovies = MutableLiveData<List<RemoteMovieDetail>>()
    val movies: LiveData<List<RemoteMovieDetail>> get() = mMovies

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MovieService::class.java)
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onError: () -> Unit) {
        val apiKey: String = BuildConfig.MOVIE_API_KEY
        api.getUpcomingMovies(page, apiKey)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                        call: Call<GetMoviesResponse>,
                        response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            mMoviesList = responseBody.movies
                            mMovies.value = mMoviesList
                        } else {
                            onError.invoke()
                            Log.d("MoviesRepository: ", "Error")
                        }
                    }
                    else {
                        onError.invoke()
                        Log.d("MoviesRepository: ", "Error")
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                    Log.d("MoviesRepository: ", "Error")
                }
            })
    }
}