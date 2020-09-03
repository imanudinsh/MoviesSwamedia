package com.im.moviecatalogue.data

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.im.moviecatalogue.data.local.LocalRepository
import com.im.moviecatalogue.data.local.entity.GenreEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.remote.ApiResponse
import com.im.moviecatalogue.data.remote.RemoteRepository
import com.im.moviecatalogue.data.remote.response.Review
import com.im.moviecatalogue.utils.AppExecutors
import com.im.moviecatalogue.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieRepository private constructor(
    @param:NonNull private val localRepository: LocalRepository, @param:NonNull private val remoteRepository: RemoteRepository,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    override fun allGenres(): LiveData<Resource<List<GenreEntity>>>{
        return object :
                NetworkBoundResource<List<GenreEntity>, List<GenreEntity>>(appExecutors) {
                override fun shouldFetch(data: List<GenreEntity>): Boolean? {
                    return data.isEmpty()
                }

                override fun loadFromDB(): LiveData<List<GenreEntity>> {
                    return localRepository.allGenres()
                }

                override fun createCall(): LiveData<ApiResponse<List<GenreEntity>>> {

                    return remoteRepository.allGenresAsLiveData()
                }

                override fun saveCallResult(data: List<GenreEntity>) {
                    localRepository.insertGenres(data)
                }
            }.asLiveData()
    }

    override fun allMovies(page: String, genres: String): LiveData<Resource<List<MovieEntity>>>{
        return object :
                NetworkBoundResource<List<MovieEntity>, List<MovieEntity>>(appExecutors) {
                override fun shouldFetch(data: List<MovieEntity>): Boolean? {
                    return data.isEmpty() || page.toInt() > 1
                }

                override fun loadFromDB(): LiveData<List<MovieEntity>> {
                    return localRepository.allMovies(page.toInt())
                }

                override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> {

                    Log.d("MovieViewModel", "data $page")
                    return remoteRepository.allMoviesAsLiveData(page, genres)
                }

                override fun saveCallResult(data: List<MovieEntity>) {
                    localRepository.insertMovies(data)
                }
            }.asLiveData()
    }

    override fun trailer(id: String): LiveData<Resource<String>> {
        Log.d("MovieRepository","$id")

        val trailerKey = MutableLiveData<String>()
        trailerKey.value = ""

        val tralerResource = object :
            NetworkBoundResource<String, String>(appExecutors) {
            override fun loadFromDB(): LiveData<String> {
                return trailerKey
            }

            override fun saveCallResult(data: String) {
                trailerKey.postValue(data)

            }

            override fun shouldFetch(data:String): Boolean? {
                return true
            }



            override fun createCall(): LiveData<ApiResponse<String>> {

                Log.d("MovieRepository","$id ")
                return remoteRepository.getTrailer(id)
            }

        }.asLiveData()

        return tralerResource
    }

    override fun review(id: String, page: String): LiveData<Resource<List<Review>>> {
        Log.d("MovieRepository","$id")

        val list : List<Review> = listOf()
        val reviews = MutableLiveData<List<Review>>()
        reviews.value = list

        return object :
            NetworkBoundResource<List<Review>, List<Review>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Review>> {
                return reviews
            }

            override fun saveCallResult(data: List<Review>) {
                reviews.postValue(data)

            }

            override fun shouldFetch(data: List<Review>): Boolean? {
                return true
            }



            override fun createCall(): LiveData<ApiResponse<List<Review>>> {

                Log.d("MovieRepository","$id ")
                return remoteRepository.getReview(id, page)
            }

        }.asLiveData()
    }




    companion object {

        @Volatile
        var INSTANCE: MovieRepository? = null

        fun getInstance(
            localRepository: LocalRepository,
            remoteData: RemoteRepository,
            appExecutors: AppExecutors
        ): MovieRepository? {
            if (INSTANCE == null) {
                synchronized(MovieRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MovieRepository(localRepository, remoteData, appExecutors)
                    }
                }
            }
            return INSTANCE
        }
    }
}

