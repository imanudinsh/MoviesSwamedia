package com.im.moviecatalogue.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.di.Injection
import com.im.moviecatalogue.ui.main.MoviesViewModel
import com.im.moviecatalogue.ui.moviedetail.MovieDetailViewModel

class ViewModelFactory private constructor(private val mMovieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {

            return MoviesViewModel(mMovieRepository) as T
        }else if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {

            return MovieDetailViewModel(mMovieRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(Injection.provideRepository(application))
                    }
                }
            }
            return INSTANCE
        }
    }

}
