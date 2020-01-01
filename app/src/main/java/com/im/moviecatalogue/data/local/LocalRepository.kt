package com.im.moviecatalogue.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.im.moviecatalogue.data.local.entity.FavoriteEntity

import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity
import com.im.moviecatalogue.data.local.room.MovieDao
import androidx.paging.DataSource


class LocalRepository private constructor(private val mMovieDao: MovieDao) {

    // TV Shows

    fun allTvShows(): LiveData<List<TvShowEntity>>
    {
        return mMovieDao.getAllTvShows()
    } 

    fun insertTvShows(tv: List<TvShowEntity>) {
        mMovieDao.insertTvShows(tv)
    }



    // Movies

    fun allMovies(): LiveData<List<MovieEntity>>
    {
        return mMovieDao.getAllMovies()
    }

    fun insertMovies(movies: List<MovieEntity>) {
        mMovieDao.insertMovies(movies)
    }


    // Favorite

    fun allFavorites(category: String): DataSource.Factory<Int, FavoriteEntity>
    {
        return mMovieDao.getFavorites(category)
    }

    fun favoriteById(movieId: String, category: String): LiveData<List<FavoriteEntity>>
    {
        Log.d("LocalRepo","$movieId $category")
        return mMovieDao.getFavoriteById(movieId, category)
    }

    fun insertFavorite(favorite: FavoriteEntity) {
        mMovieDao.insertFavorite(favorite)
    }

    fun deleteFavorite(favorite: FavoriteEntity){
        mMovieDao.deleteFavorite(favorite)
    }


    companion object {

        private var INSTANCE: LocalRepository? = null

        fun getInstance(movieDao: MovieDao): LocalRepository {
            if (INSTANCE == null) {
                INSTANCE = LocalRepository(movieDao)
            }
            return INSTANCE as LocalRepository
        }
    }
}