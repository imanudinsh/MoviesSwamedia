package com.im.moviecatalogue.data.remote


import com.im.moviecatalogue.BuildConfig
import com.im.moviecatalogue.data.remote.response.MovieResponse
import com.im.moviecatalogue.data.remote.response.TrailerResponse
import com.im.moviecatalogue.data.remote.response.TvShowResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface ApiInteractor {

    @GET("${ApiService.URL}discover/movie?api_key=${BuildConfig.MOVIE_API_KEY}&language=en-US")
    fun getMovie(): Deferred<MovieResponse>

    @GET("${ApiService.URL}discover/tv?api_key=${BuildConfig.MOVIE_API_KEY}&language=en-US")
    fun getTvShow(): Deferred<TvShowResponse>

    @GET("${ApiService.URL}{category}/{id}/videos?api_key=${BuildConfig.MOVIE_API_KEY}&language=en-US")
    fun getTrailer(@Path("id") id: String,
                   @Path("category") category: String): Deferred<TrailerResponse>


}