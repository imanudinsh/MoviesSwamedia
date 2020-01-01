package com.im.moviecatalogue.data.remote

import com.im.moviecatalogue.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiService
{
    companion object {
      const val URL: String = "https://api.themoviedb.org/3/"

        var retrofit: Retrofit? = null

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val client: Retrofit
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(URL)
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build()
                }
                return retrofit!!
            }


    }
}