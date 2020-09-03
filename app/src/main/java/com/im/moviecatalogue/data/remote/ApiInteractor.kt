package com.im.moviecatalogue.data.remote


import com.im.moviecatalogue.BuildConfig
import com.im.moviecatalogue.data.remote.response.GenreResponse
import com.im.moviecatalogue.data.remote.response.MovieResponse
import com.im.moviecatalogue.data.remote.response.ReviewResponse
import com.im.moviecatalogue.data.remote.response.TrailerResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface ApiInteractor {

    @GET("${ApiService.URL}discover/movie")
    fun getMovie(@QueryMap data: Map<String, String>): Deferred<MovieResponse>

    @GET("${ApiService.URL}movie/{id}/videos?api_key=${BuildConfig.MOVIE_API_KEY}&language=en-US")
    fun getTrailer(@Path("id") id: String): Deferred<TrailerResponse>

    @GET("${ApiService.URL}movie/{id}/reviews")
    fun getReview(@Path("id") id: String,
                  @QueryMap data: Map<String, String>): Deferred<ReviewResponse>

    @GET("${ApiService.URL}genre/movie/list")
    fun getGenre(@QueryMap data: Map<String, String>): Deferred<GenreResponse>


}