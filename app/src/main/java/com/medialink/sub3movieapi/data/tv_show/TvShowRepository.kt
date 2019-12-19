package com.medialink.sub3movieapi.data.tv_show

import android.util.Log
import com.google.gson.Gson
import com.medialink.sub2catalogue.models.tv_show.TvShowRespon
import com.medialink.sub3movieapi.OperationCallback
import com.medialink.sub3movieapi.model.error.ErrorRespon
import com.medialink.sub3movieapi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowRepository : TvShowDataSource {

    private var call: Call<TvShowRespon>? = null
    override var page: Int = 1
    override var language: String = "en-US"

    override fun getTvShows(callback: OperationCallback) {
        call = RetrofitClient.getApiTvShow().getTvShowPopular(page, language)
        call?.enqueue(object : Callback<TvShowRespon> {
            override fun onFailure(call: Call<TvShowRespon>, t: Throwable) {
                callback.onError(t)
                Log.d("debug", t.message.toString())
            }

            override fun onResponse(call: Call<TvShowRespon>, response: Response<TvShowRespon>) {
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