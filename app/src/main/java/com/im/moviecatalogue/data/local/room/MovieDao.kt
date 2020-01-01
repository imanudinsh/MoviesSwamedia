package com.im.moviecatalogue.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.paging.DataSource
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>): LongArray

    @Query("SELECT * FROM movieentities")
    fun getAllMovies(): LiveData<List<MovieEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>): LongArray

    @Query("SELECT * FROM tvshowentities")
    fun getAllTvShows(): LiveData<List<TvShowEntity>>


    // Favorite


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity): Long

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favoriteentities WHERE id = :id and category = :category")
    fun getFavoriteById(id: String, category: String): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favoriteentities WHERE category = :category")
    fun getFavorites(category: String): DataSource.Factory<Int, FavoriteEntity>
}