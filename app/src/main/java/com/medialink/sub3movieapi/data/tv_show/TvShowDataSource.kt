package com.medialink.sub3movieapi.data.tv_show

import com.medialink.sub3movieapi.OperationCallback

interface TvShowDataSource {
    var page: Int
    var language: String

    fun getTvShows(callback: OperationCallback)
    fun cancel()
}