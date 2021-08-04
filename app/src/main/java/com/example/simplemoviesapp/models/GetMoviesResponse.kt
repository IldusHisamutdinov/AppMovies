package com.example.simplemoviesapp.models

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
        @SerializedName("page") val page: Int,
        @SerializedName("results") val movies: List<RemoteMovieDetail>,
        @SerializedName("total_pages") val pages: Int
)
