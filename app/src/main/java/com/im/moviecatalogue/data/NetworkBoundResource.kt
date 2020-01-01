package com.im.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.im.moviecatalogue.data.remote.ApiResponse
import com.im.moviecatalogue.data.remote.StatusResponse
import com.im.moviecatalogue.utils.AppExecutors
import com.im.moviecatalogue.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.setValue(Resource.loading(null))

        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)

            if (shouldFetch(data)!!) {
                fetchFromNetwork(dbSource)
//                runBlocking {
//                    launch {
//                    }
//                }
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType): Boolean?

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(
            dbSource
        ) { newData -> result.setValue(Resource.loading(newData)) }
        result.addSource<ApiResponse<RequestType>>(apiResponse) { response ->

            result.removeSource<ApiResponse<RequestType>>(apiResponse)
            result.removeSource(dbSource)

            when (response.status) {
                StatusResponse.SUCCESS -> mExecutors.diskIO().execute({

                    response.body?.let {
                        saveCallResult(it)
                    }

                    mExecutors.mainThread().execute({
                        result.addSource(
                            loadFromDB()
                        ) { newData -> result.setValue(Resource.success(newData)) }
                    })

                })

                StatusResponse.EMPTY -> mExecutors.mainThread().execute({
                    result.addSource(
                        loadFromDB()
                    ) { newData -> result.setValue(Resource.success(newData)) }
                })
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.setValue(
                            Resource.error(
                                response.message!!,
                                newData
                            )
                        )
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }
}