package com.im.moviecatalogue.data


import androidx.lifecycle.LiveData
import com.im.moviecatalogue.data.local.entity.GenreEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.remote.response.Review
import com.im.moviecatalogue.vo.Resource

interface MovieDataSource {
    fun allGenres(): LiveData<Resource<List<GenreEntity>>>
    fun allMovies(page: String, genres: String): LiveData<Resource<List<MovieEntity>>>
    fun trailer(id: String) : LiveData<Resource<String>>
    fun review(id: String, page: String): LiveData<Resource<List<Review>>>
}