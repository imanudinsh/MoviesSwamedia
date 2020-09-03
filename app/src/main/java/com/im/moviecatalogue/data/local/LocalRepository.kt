package com.im.moviecatalogue.data.local

import androidx.lifecycle.LiveData
import com.im.moviecatalogue.data.local.entity.GenreEntity

import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.room.GenreDao
import com.im.moviecatalogue.data.local.room.MovieDao


class LocalRepository private constructor(private val mMovieDao: MovieDao, private val mGenreDao: GenreDao) {


    fun allMovies(page : Int): LiveData<List<MovieEntity>>
    {
        val offset = ((page-1)* 20)
        return mMovieDao.getAllMovies(offset)
    }

    fun insertMovies(movies: List<MovieEntity>) {
        mMovieDao.insertMovies(movies)
    }

    fun allGenres(): LiveData<List<GenreEntity>>
    {
        return mGenreDao.getAllGenre()
    }

    fun insertGenres(genres: List<GenreEntity>) {
        mGenreDao.insertGenres(genres)
    }

    companion object {

        private var INSTANCE: LocalRepository? = null

        fun getInstance(movieDao: MovieDao, genreDao: GenreDao): LocalRepository {
            if (INSTANCE == null) {
                INSTANCE = LocalRepository(movieDao, genreDao)
            }
            return INSTANCE as LocalRepository
        }
    }
}