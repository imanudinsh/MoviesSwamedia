package com.im.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.im.moviecatalogue.data.local.LocalRepository
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.local.entity.TvShowEntity
import com.im.moviecatalogue.data.remote.RemoteRepository
import com.im.moviecatalogue.utils.FakeDataDummy
import com.im.moviecatalogue.utils.InstantAppExecutors
import com.im.moviecatalogue.utils.LiveDataTestUtil
import com.im.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MovieRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val local: LocalRepository = mock()
    private val remote: RemoteRepository = mock()
    private val instantAppExecutors: InstantAppExecutors = mock()
    private val academyRepository = FakeMovieRepository(local, remote, instantAppExecutors)

    @Test
    fun getAllMovies() {
        val dummyMovies = MutableLiveData<List<MovieEntity>>()
        dummyMovies.setValue(FakeDataDummy().generateDummyMovies())

        whenever(local.allMovies()).thenReturn(dummyMovies)
        val result = LiveDataTestUtil.getValue(academyRepository.allMovies)

        verify(local).allMovies()
        assertNotNull(result.data)
        assertEquals(FakeDataDummy().generateDummyMovies().size, result.data?.size)
    }


    @Test
    fun getAllTvShows() {
        val dummyMovies = MutableLiveData<List<TvShowEntity>>()
        dummyMovies.setValue(FakeDataDummy().generateDummyTvShows())

        whenever(local.allTvShows()).thenReturn(dummyMovies)
        val result = LiveDataTestUtil.getValue(academyRepository.allTvShows)

        verify(local).allTvShows()
        assertNotNull(result.data)
        assertEquals(FakeDataDummy().generateDummyTvShows().size, result.data?.size)
    }

}