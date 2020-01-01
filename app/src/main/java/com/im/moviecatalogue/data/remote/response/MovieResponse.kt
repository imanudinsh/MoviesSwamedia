package com.im.moviecatalogue.data.remote.response

import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity


data class MovieResponse (
    var page: Int,
    var results: List<MovieEntity>
)

data class TvShowResponse (
    var page: Int,
    var results: List<TvShowEntity>
)