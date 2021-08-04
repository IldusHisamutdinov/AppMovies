package com.example.simplemoviesapp.details

import com.example.simplemoviesapp.models.RemoteMovieDetail

sealed class Resource {
    data class Success(val serverResponseData: RemoteMovieDetail) : Resource()
    data class Error(val error: Throwable) : Resource()
    data class Loading(val progress: Int?) : Resource()
}
