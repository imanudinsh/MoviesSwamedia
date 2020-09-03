package com.im.moviecatalogue.data.remote

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.im.moviecatalogue.BuildConfig
import com.im.moviecatalogue.data.local.entity.GenreEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.remote.response.Review
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class RemoteRepository() {
    private val apiServices = ApiService.client.create(ApiInteractor::class.java)

    fun allGenresAsLiveData(): LiveData<ApiResponse<List<GenreEntity>>>{
        val result = MutableLiveData<ApiResponse<List<GenreEntity>>>()
        val request = mapOf(
            "api_key" to BuildConfig.MOVIE_API_KEY,
            "language" to "en-US"
        )

        try {
            runBlocking {
                launch {
                    Log.d("MovieViewModel", "data $request")
                    val responses = apiServices.getGenre(request).await()
                    Log.d("MovieViewModel", "result  $responses")
                    if(responses.genres.isNotEmpty()){
                        result.value = ApiResponse.success(responses.genres)
                    }else
                    {
                        result.value = ApiResponse.empty("Movie not available", responses.genres)
                    }
                }
            }

        }catch (ex: Exception){
            Log.d("MovieViewModel", "result error  ${ex.message}")

            result.value = ApiResponse.empty("${ex.message}", null)
        }
        return result
    }


    fun allMoviesAsLiveData(page: String, genres: String): LiveData<ApiResponse<List<MovieEntity>>>{
        val result = MutableLiveData<ApiResponse<List<MovieEntity>>>()
        val request = mapOf(
            "api_key" to BuildConfig.MOVIE_API_KEY,
            "language" to "en-US",
            "page" to page
        )

        try {
            runBlocking {
                launch {
                    Log.d("MovieViewModel", "data $request")
                    val responses = apiServices.getMovie(request).await()
                    Log.d("MovieViewModel", "result  $responses")
                    if(responses.results.isNotEmpty()){
                        result.value = ApiResponse.success(responses.results)
                    }else
                    {
                        result.value = ApiResponse.empty("Movie not available", responses.results)
                    }
                }
            }

        }catch (ex: Exception){
            Log.d("MovieViewModel", "result error  ${ex.message}")

            result.value = ApiResponse.empty("${ex.message}", null)
        }
        return result
    }

    fun getTrailer(id: String): LiveData<ApiResponse<String>>{
        println("getTrailer")
        val result = MutableLiveData<ApiResponse<String>>()
        try {
            runBlocking {
                launch {
                    val responses = apiServices.getTrailer(id = id).await()
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


        }catch (ex: Exception){
            result.postValue(ApiResponse.empty("${ex.message}", null))
        }
        return result
    }

    fun getReview(id: String, page: String): LiveData<ApiResponse<List<Review>>>{
        val request = mapOf(
            "api_key" to BuildConfig.MOVIE_API_KEY,
            "language" to "en-US",
            "page" to page
        )
        val result = MutableLiveData<ApiResponse<List<Review>>>()
        try {
            runBlocking {
                launch {
                    val responses = apiServices.getReview(id = id, data = request).await()
                    println("getTrailer $responses")
                    responses.results?.let {
                        if(it.size > 0){
                            result.postValue( ApiResponse.success(responses.results))
                        }else
                        {
                            result.postValue(ApiResponse.empty("Trailer not available", responses.results))
                        }
                    }
                }
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