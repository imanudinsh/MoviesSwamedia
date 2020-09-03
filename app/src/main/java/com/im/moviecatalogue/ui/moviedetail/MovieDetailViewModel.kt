package com.im.moviecatalogue.ui.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.data.remote.response.Review
import com.im.moviecatalogue.vo.Resource

/**
 * Created by Imanudin Sholeh on 09/20/2019.
 */

class MovieDetailViewModel(mMovieRepository: MovieRepository): ViewModel() {


    private  var movieRepository: MovieRepository = mMovieRepository

    val movieId = MutableLiveData<String>()
    val page = MutableLiveData<Int>()

    var trailerMovie = Transformations.switchMap<String, Resource<String>>(
        movieId
    ) {
       movieRepository.trailer(movieId.value.toString())
    }

    var reviewMovie = Transformations.switchMap<Int, Resource<List<Review>>>(
        page
    ) {
       movieRepository.review(
           id = movieId.value.toString(),
           page = page.value.toString()
       )
    }


}
