package com.im.moviecatalogue.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.im.moviecatalogue.data.local.entity.GenreEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(movies: List<GenreEntity>): LongArray

    @Query("SELECT * FROM genreentities")
    fun getAllGenre(): LiveData<List<GenreEntity>>

}