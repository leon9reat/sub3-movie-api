package com.medialink.sub3movieapi.data.movie

import com.medialink.sub3movieapi.OperationCallback

interface MovieDataSource {
    var page: Int
    var language: String

    fun getMovies(callback: OperationCallback)
    fun cancel()
}