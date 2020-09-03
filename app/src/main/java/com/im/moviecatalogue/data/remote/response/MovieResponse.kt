package com.im.moviecatalogue.data.remote.response

import com.im.moviecatalogue.data.local.entity.MovieEntity


data class MovieResponse (
    var page: Int,
    var results: List<MovieEntity>
)
