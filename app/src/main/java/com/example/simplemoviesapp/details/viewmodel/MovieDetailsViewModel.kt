package com.example.simplemoviesapp.details.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplemoviesapp.api.RetrofitImpl
import com.example.simplemoviesapp.details.Resource
import com.example.simplemoviesapp.models.RemoteMovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel(
    private val liveData: MutableLiveData<Resource> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataRequest(): LiveData<Resource> {
        return liveData
    }
    fun sendServerRequestDate(id: String) {
        liveData.value = Resource.Loading(null)
        val apiKey = "274f828ad283bd634ef4fc1ee4af255f"
        if (apiKey.isBlank()) {
            Resource.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getSingleMovie(id, apiKey, "ru-RU").enqueue(object :
                Callback<RemoteMovieDetail> {
                override fun onResponse(
                    call: Call<RemoteMovieDetail>,
                    response: Response<RemoteMovieDetail>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.run{
                            liveData.value =
                                Resource.Success(this)}
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveData.value =
                                Resource.Error(Throwable("Unidentified error"))
                        } else {
                            liveData.value =
                                Resource.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<RemoteMovieDetail>, t: Throwable) {
                    liveData.value = Resource.Error(t)
                }
            })
        }
    }

}