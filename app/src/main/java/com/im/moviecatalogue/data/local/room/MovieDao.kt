package com.im.moviecatalogue.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.im.moviecatalogue.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>): LongArray

    @Query("SELECT * FROM movieentities LIMIT 20 OFFSET :offset")
    fun getAllMovies(offset: Int): LiveData<List<MovieEntity>>

}