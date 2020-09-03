package com.im.moviecatalogue.data.remote.response

import com.im.moviecatalogue.data.local.entity.GenreEntity


data class GenreResponse (
    val genres: List<GenreEntity>
)

