package com.im.moviecatalogue.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity


@Database(entities = [
    MovieEntity::class,
    TvShowEntity::class,
    FavoriteEntity::class
], version = 4, exportSchema = false)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

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