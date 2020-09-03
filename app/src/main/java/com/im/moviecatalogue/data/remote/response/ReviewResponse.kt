package com.im.moviecatalogue.data.remote.response

import com.google.gson.annotations.SerializedName


data class ReviewResponse (
    val id: Long,
    val page: Long,
    val results: List<Review>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long
)
