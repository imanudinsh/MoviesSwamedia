package com.im.moviecatalogue.ui.moviedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.vo.Resource

/**
 * Created by Imanudin Sholeh on 09/20/2019.
 */

class MovieDetailViewModel(mMovieRepository: MovieRepository): ViewModel() {


    private  var movieRepository: MovieRepository = mMovieRepository

    val movieId = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    lateinit var isFavorite:LiveData<List<FavoriteEntity>>

    var trailerMovie = Transformations.switchMap<String, Resource<String>>(
        movieId
    ) {
       movieRepository.trailer(movieId.value.toString(), category.value.toString())
    }

    internal fun setFavoriite(movieId: String, category: String){
        isFavorite = movieRepository.favoriteById(movieId, category)
    }


    internal fun insertFavorite(fav: FavoriteEntity){
        movieRepository.insertFavorite(fav)
        Log.d("viewmodel","delete $fav")
    }

    internal fun deleteFavorite(fav: FavoriteEntity){
        movieRepository.deleteFavorite(fav)
        Log.d("viewmodel","delete $fav")
    }

}
