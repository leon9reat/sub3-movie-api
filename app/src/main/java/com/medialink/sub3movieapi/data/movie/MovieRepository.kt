package com.medialink.sub3movieapi.data.movie

import android.util.Log
import com.google.gson.Gson
import com.medialink.sub2catalogue.models.movie.MovieRespon
import com.medialink.sub3movieapi.OperationCallback
import com.medialink.sub3movieapi.model.error.ErrorRespon
import com.medialink.sub3movieapi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository : MovieDataSource {
    private var call: Call<MovieRespon>? = null

    override var page: Int = 1
    override var language: String = "en-US"

    override fun getMovies(callback: OperationCallback) {
        call = RetrofitClient.getApiMovie().getMoviePopular(page, language)
        call?.enqueue(object : Callback<MovieRespon> {
            override fun onFailure(call: Call<MovieRespon>, t: Throwable) {
                callback.onError(t)
                Log.d("debug", t.message.toString())
            }

            override fun onResponse(call: Call<MovieRespon>, response: Response<MovieRespon>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body()?.results)
                    Log.d("debug", "success: ${response.body()?.totalResults}")
                } else {
                    val jsonString = response.errorBody()?.charStream()?.readText() ?: "{}"
                    val error400 = Gson().fromJson(jsonString, ErrorRespon::class.java)
                    when (response.code()) {
                        401 -> callback.onError(error400.statusMessage)
                        404 -> callback.onError(error400.statusMessage)
                        else -> callback.onError(jsonString)
                    }
                }
            }

        })
    }

    override fun cancel() {
        call?.cancel()
    }
}