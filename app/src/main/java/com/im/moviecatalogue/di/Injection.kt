package com.im.moviecatalogue.di

import android.app.Application

import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.data.local.LocalRepository
import com.im.moviecatalogue.data.local.room.MovieDatabase
import com.im.moviecatalogue.data.remote.ApiInteractor
import com.im.moviecatalogue.data.remote.RemoteRepository
import com.im.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(application: Application): MovieRepository {

        val database = MovieDatabase.getAppDataBase(application)

        val localRepository = LocalRepository.getInstance(database!!.movieDao())
        val remoteRepository = RemoteRepository.getInstance()
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(localRepository, remoteRepository, appExecutors)!!
    }
}
