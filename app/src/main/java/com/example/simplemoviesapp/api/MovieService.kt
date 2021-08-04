package com.example.simplemoviesapp.api

import com.example.simplemoviesapp.models.GetMoviesResponse
import com.example.simplemoviesapp.models.RemoteMovieDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "ru-RU"
    ): Call<GetMoviesResponse>

    @GET("movie/{id}")
    fun getSingleMovie(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "ru-RU"
    ): Call<RemoteMovieDetail>
}