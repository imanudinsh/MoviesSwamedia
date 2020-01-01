package com.im.moviecatalogue.data.remote.response

import com.im.moviecatalogue.data.remote.response.Trailer

data class TrailerResponse (
    var id: Int? = 0,
    var results: List<Trailer>? = null
)