package com.im.moviecatalogue.ui.main


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity
import com.im.moviecatalogue.utils.values.CategoryEnum
import com.im.moviecatalogue.vo.Resource


class MoviesViewModel(mMovieRepository: MovieRepository) : ViewModel() {

     var movieRepository: MovieRepository = mMovieRepository
     val movieId = MutableLiveData<String>()
     val allFavorite = movieRepository.allFavorite(CategoryEnum.MOVIE.value)
     val allFavoriteTvShow = movieRepository.allFavorite(CategoryEnum.TV.value)


//     var movies =  movieRepository.allMovies()
//     var tvShows =  movieRepository.allTvShows

     var movies = Transformations.switchMap<String, Resource<List<MovieEntity>>>(
          movieId
     ) {
          movieRepository.allMovies
     }

     var tvShows = Transformations.switchMap<String, Resource<List<TvShowEntity>>>(
          movieId
     ) {
          movieRepository.allTvShows
     }


     internal fun insertFavorite(fav: FavoriteEntity){
          movieRepository.insertFavorite(fav)
     }

     internal fun deleteFavorite(fav: FavoriteEntity){
          movieRepository.deleteFavorite(fav)
     }





}
