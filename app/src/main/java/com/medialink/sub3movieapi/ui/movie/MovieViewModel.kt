package com.medialink.sub3movieapi.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medialink.sub2catalogue.models.movie.Movie
import com.medialink.sub3movieapi.OperationCallback
import com.medialink.sub3movieapi.data.movie.MovieRepository
import java.util.*

class MovieViewModel : ViewModel() {

    private val movieRepository = MovieRepository()

    private val _movies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    val movies: LiveData<List<Movie>> = _movies

    private val _isViewLoading = MutableLiveData<Boolean>().apply { value = false }
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _isEmptyList = MutableLiveData<Boolean>().apply { value = false }
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    var lokal: String = Locale.getDefault().language

    init {
        loadMovie(movieRepository.page)
    }

    fun loadMovie(page: Int) {
        _isViewLoading.value = true

        movieRepository.page = page
        val iso3Country = Locale.getDefault().country
        if (lokal.equals("in", ignoreCase = true)) {
            movieRepository.language = "id-${iso3Country}"
        } else {
            movieRepository.language = "${lokal}-${iso3Country}"
        }

        movieRepository.getMovies(object : OperationCallback {
            override fun onSuccess(obj: Any?) {
                _isViewLoading.value = false
                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        _isEmptyList.value = true
                    } else {
                        //_isEmptyList.value = true
                        //_movies.value = obj as List<Movie>
                        _movies.postValue(obj as List<Movie>?)
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