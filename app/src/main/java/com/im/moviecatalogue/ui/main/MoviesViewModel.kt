package com.im.moviecatalogue.ui.main


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.data.local.entity.GenreEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.vo.Resource


class MoviesViewModel(mMovieRepository: MovieRepository) : ViewModel() {

     var movieRepository: MovieRepository = mMovieRepository
     val movieId = MutableLiveData<String>()
     val page = MutableLiveData<Int>()
     val genres = MutableLiveData<String>()


     var movies = Transformations.switchMap<Int, Resource<List<MovieEntity>>>(
          page
     ) {
          movieRepository.allMovies(
               page = page.value.toString(),
               genres = genres.value.toString()
          )
     }

     var allGenres = Transformations.switchMap<Int, Resource<List<GenreEntity>>>(
          page
     ) {
          movieRepository.allGenres()
     }

}
