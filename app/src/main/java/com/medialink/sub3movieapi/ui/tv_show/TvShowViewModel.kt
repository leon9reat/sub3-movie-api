package com.medialink.sub3movieapi.ui.tv_show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medialink.sub2catalogue.models.movie.Movie
import com.medialink.sub2catalogue.models.tv_show.TvShow
import com.medialink.sub3movieapi.OperationCallback
import com.medialink.sub3movieapi.data.tv_show.TvShowRepository
import java.util.*

class TvShowViewModel : ViewModel() {

    private val tvShowRepository = TvShowRepository()

    private val _tvShow = MutableLiveData<List<TvShow>>().apply { value = emptyList() }
    val tvShow: LiveData<List<TvShow>> = _tvShow

    private val _isViewLoading = MutableLiveData<Boolean>().apply { value = false }
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _isEmptyList = MutableLiveData<Boolean>().apply { value = false }
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    var lokal: String = Locale.getDefault().language

    init {
        loadTvShow(tvShowRepository.page)
    }

    fun loadTvShow(page: Int) {
        _isViewLoading.value = true

        tvShowRepository.page = page
        val iso3Country = Locale.getDefault().country
        if (lokal.equals("in", ignoreCase = true)) {
            tvShowRepository.language = "id-${iso3Country}"
        } else {
            tvShowRepository.language = "${lokal}-${iso3Country}"
        }

        tvShowRepository.getTvShows(object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                _isViewLoading.value = false
                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        _isEmptyList.value = true
                    } else {
                        _tvShow.postValue(obj as List<TvShow>?)
                    }
                }
            }

            override fun onError(obj: Any?) {
                _isViewLoading.value = false
                _onMessageError.value = obj
            }
        })
    }

}