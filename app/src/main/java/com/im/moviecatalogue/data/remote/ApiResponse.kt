package com.im.moviecatalogue.data.remote

import com.im.moviecatalogue.data.remote.StatusResponse.EMPTY
import com.im.moviecatalogue.data.remote.StatusResponse.ERROR
import com.im.moviecatalogue.data.remote.StatusResponse.SUCCESS

class ApiResponse<T>(val status: StatusResponse, val body: T?, val message: String?) {
    companion object {

        fun <T> success(body: T?): ApiResponse<T> {
            return ApiResponse(SUCCESS, body, null)
        }

        fun <T> empty(msg: String, body: T?): ApiResponse<T> {
            return ApiResponse(EMPTY, body, msg)
        }

        fun <T> error(msg: String, body: T?): ApiResponse<T> {
            return ApiResponse(ERROR, body, msg)
        }
    }

}

