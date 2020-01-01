package com.im.moviecatalogue.ui.main

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity
import com.im.moviecatalogue.data.remote.response.MovieResponse
import com.im.moviecatalogue.utils.FakeDataDummy
import com.im.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class MoviesViewModelTest{

    private var movies: List<MovieEntity>? = null

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MoviesViewModel
    private var movieRepository: MovieRepository = mock(MovieRepository::class.java)


    @Before
    fun setUp() {
        viewModel = MoviesViewModel(movieRepository)
    }

    @Test
    fun getMovies() {
        val movieEntities: Resource<List<MovieEntity>> = Resource.success(FakeDataDummy().generateDummyMovies())
        println("$movieEntities")
        val dummyMovie = MutableLiveData<Resource<List<MovieEntity>>>()
        dummyMovie.value = movieEntities
        whenever(movieRepository.allMovies).thenReturn(dummyMovie)


        viewModel.movieId.value = "1"
        val observ: Observer<Resource<List<MovieEntity>>> = mock()
        viewModel.movies.observeForever(observ)

        verify(observ).onChanged(movieEntities)
    }

    @Test
    fun getTvShows() {
        val tvShowEntities: Resource<List<TvShowEntity>> = Resource.success(FakeDataDummy().generateDummyTvShows())
        val dummyMovie = MutableLiveData<Resource<List<TvShowEntity>>>()
        dummyMovie.value = tvShowEntities
        whenever(movieRepository.allTvShows).thenReturn(dummyMovie)


        viewModel.movieId.value = "1"
        val observ: Observer<Resource<List<TvShowEntity>>> = mock()
        viewModel.tvShows.observeForever(observ)

        verify(observ).onChanged(tvShowEntities)
    }

}