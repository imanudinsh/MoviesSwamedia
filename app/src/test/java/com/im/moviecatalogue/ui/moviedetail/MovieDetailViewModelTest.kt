package com.im.moviecatalogue.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.im.moviecatalogue.data.MovieRepository
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MovieDetailViewModelTest{



    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MovieDetailViewModel
    private var movieRepository: MovieRepository = mock(MovieRepository::class.java)
    val movieId = "920"
    val category = "Movie"
    val trailer = "SbXIj2T-_uk"


    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(movieRepository)
        viewModel.movieId.postValue(movieId)
        viewModel.category.postValue(category)
    }

    @Test
    fun getMovies() {
        val traillerResponse = Resource.success(trailer)
        val dummyTrailer = MutableLiveData<Resource<String>>()
        dummyTrailer.postValue(traillerResponse)
        whenever(movieRepository.trailer("920", "Movie")).thenReturn(dummyTrailer)

        val observ: Observer<Resource<String>> = mock()
        viewModel.trailerMovie.observeForever(observ)
        verify(observ).onChanged(dummyTrailer.value)
    }

}