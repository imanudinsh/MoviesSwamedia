package com.im.moviecatalogue.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.im.moviecatalogue.data.local.entity.GenreEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity


@Database(entities = [
    MovieEntity::class,
    GenreEntity::class
], version = 4, exportSchema = false)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao

    companion object {
        var INSTANCE: MovieDatabase? = null

        fun getAppDataBase(context: Context): MovieDatabase? {
            if (INSTANCE == null){
                synchronized(MovieDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "Movies.db")
                                .fallbackToDestructiveMigration()
                                .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}