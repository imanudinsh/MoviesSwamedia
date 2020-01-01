package com.im.moviecatalogue.data.remote

import android.os.Handler
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity
import com.im.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class RemoteRepository() {
    private val SERVICE_LATENCY_IN_MILLIS: Long = 2000
    private val apiServices = ApiService.client.create(ApiInteractor::class.java)

    fun allMoviesAsLiveData(): LiveData<ApiResponse<List<MovieEntity>>>{
        val result = MutableLiveData<ApiResponse<List<MovieEntity>>>()
        try {
            EspressoIdlingResource.increment()
            runBlocking {
                launch {
                    val responses = apiServices.getMovie().await()
                    if(responses.results.size > 0){
                        result.value = ApiResponse.success(responses.results)
                    }else
                    {
                        result.value = ApiResponse.empty("Movie not available", responses.results)
                    }
                }
            }

            if (!EspressoIdlingResource.espressoIdlingResource.isIdleNow()) {
                EspressoIdlingResource.decrement()
            }
        }catch (ex: Exception){

            result.value = ApiResponse.empty("${ex.message}", null)
        }
        return result
    }

    fun allTvShowsAsLiveData(): LiveData<ApiResponse<List<TvShowEntity>>>{
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<List<TvShowEntity>>>()
        try {
            runBlocking {
                launch {
                    val responses = apiServices.getTvShow().await()
                    if(responses.results.size > 0){
                        result.postValue( ApiResponse.success(responses.results))
                    }else
                    {
                        result.postValue(ApiResponse.empty("Movie not available", responses.results))
                    }
                }
            }

            if (!EspressoIdlingResource.espressoIdlingResource.isIdleNow()) {
                EspressoIdlingResource.decrement()
            }
        }catch (ex: Exception){

            result.postValue(ApiResponse.empty("${ex.message}", null))
        }
        return result
    }

    fun getTrailer(id: String, category: String): LiveData<ApiResponse<String>>{
        println("getTrailer")
        val result = MutableLiveData<ApiResponse<String>>()
        try {
            EspressoIdlingResource.increment()
            runBlocking {
                launch {
                    println("getTrailer $id $category")
                    val responses = apiServices.getTrailer(id = id, category = category).await()
                    println("getTrailer $responses")
                    responses.results?.let {
                        if(it.size > 0){
                            result.postValue( ApiResponse.success(responses.results?.get(0)?.key))
                        }else
                        {
                            result.postValue(ApiResponse.empty("Trailer not available", responses.results?.get(0)?.key))
                        }
                    }
                }
            }

            if (!EspressoIdlingResource.espressoIdlingResource.isIdleNow()) {
                EspressoIdlingResource.decrement()
            }

        }catch (ex: Exception){
            result.postValue(ApiResponse.empty("${ex.message}", null))
        }
        return result
    }






    companion object {

        private var INSTANCE: RemoteRepository? = null

        fun getInstance(): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository()
            }
            return INSTANCE as RemoteRepository
        }
    }

}