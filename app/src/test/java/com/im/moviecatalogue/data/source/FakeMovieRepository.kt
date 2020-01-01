package com.im.moviecatalogue.data.source

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.im.moviecatalogue.data.MovieDataSource
import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.data.NetworkBoundResource
import com.im.moviecatalogue.data.local.LocalRepository
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity
import com.im.moviecatalogue.data.remote.ApiResponse
import com.im.moviecatalogue.data.remote.RemoteRepository
import com.im.moviecatalogue.utils.AppExecutors
import com.im.moviecatalogue.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FakeMovieRepository constructor(
    @param:NonNull private val localRepository: LocalRepository, @param:NonNull private val remoteRepository: RemoteRepository,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    override val allMovies: LiveData<Resource<List<MovieEntity>>>
        get() = object :
            NetworkBoundResource<List<MovieEntity>, List<MovieEntity>>(appExecutors) {
            override fun shouldFetch(data: List<MovieEntity>): Boolean? {
                return data.isEmpty()
            }

            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localRepository.allMovies()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> {

                return remoteRepository.allMoviesAsLiveData()
            }

            override fun saveCallResult(data: List<MovieEntity>) {
                localRepository.insertMovies(data)
            }
        }.asLiveData()

    override val allTvShows: LiveData<Resource<List<TvShowEntity>>>
        get() = object :
            NetworkBoundResource<List<TvShowEntity>, List<TvShowEntity>>(appExecutors) {
            override fun shouldFetch(data: List<TvShowEntity>): Boolean? {
                return data.isEmpty()
            }

            override fun loadFromDB(): LiveData<List<TvShowEntity>> {
                return localRepository.allTvShows()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowEntity>>> {

                return remoteRepository.allTvShowsAsLiveData()
            }

            override fun saveCallResult(data: List<TvShowEntity>) {
                localRepository.insertTvShows(data)
            }
        }.asLiveData()


    override fun trailer(id: String, category: String): LiveData<Resource<String>> {
        Log.d("MovieRepository","$id $category")

        val trailerKey = MutableLiveData<String>()
        trailerKey.value = ""

        val tralerResource = object :
            NetworkBoundResource<String, String>(appExecutors) {
            override fun loadFromDB(): LiveData<String> {
                return trailerKey
            }

            override fun saveCallResult(data: String) {
                GlobalScope.launch(Dispatchers.Main) {
                    trailerKey.value = data
                }
            }

            override fun shouldFetch(data:String): Boolean? {
                return true
            }



            override fun createCall(): LiveData<ApiResponse<String>> {

                Log.d("MovieRepository","$id $category")
                return remoteRepository.getTrailer(id, category)
            }

        }.asLiveData()

        return tralerResource
    }


    override fun allFavorite(category: String): LiveData<PagedList<FavoriteEntity>>  = LivePagedListBuilder(localRepository.allFavorites(category), 10).build()

    override fun favoriteById(movieId: String, category: String): LiveData<List<FavoriteEntity>> = localRepository.favoriteById(movieId = movieId, category = category)


    override fun insertFavorite(favorite: FavoriteEntity){
        GlobalScope.launch(Dispatchers.IO) {
            localRepository.insertFavorite(favorite)
        }
    }
    override fun deleteFavorite(favorite: FavoriteEntity){
        GlobalScope.launch(Dispatchers.IO) {
            localRepository.deleteFavorite(favorite)
        }
    }



companion object {

        @Volatile
        var INSTANCE: FakeMovieRepository? = null

        fun getInstance(
            localRepository: LocalRepository,
            remoteData: RemoteRepository,
            appExecutors: AppExecutors
        ): FakeMovieRepository? {
            if (INSTANCE == null) {
                synchronized(FakeMovieRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            FakeMovieRepository(
                                localRepository,
                                remoteData,
                                appExecutors
                            )
                    }
                }
            }
            return INSTANCE
        }
    }
}

