package com.im.moviecatalogue.data


import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity
import com.im.moviecatalogue.vo.Resource

interface MovieDataSource {
    val allMovies: LiveData<Resource<List<MovieEntity>>>
    val allTvShows: LiveData<Resource<List<TvShowEntity>>>
    fun trailer(id: String, category: String) : LiveData<Resource<String>>
    fun allFavorite(category: String): LiveData<PagedList<FavoriteEntity>>
    fun favoriteById(movieId: String, category: String): LiveData<List<FavoriteEntity>>
    fun insertFavorite(favorite: FavoriteEntity)
    fun deleteFavorite(favorite: FavoriteEntity)
}