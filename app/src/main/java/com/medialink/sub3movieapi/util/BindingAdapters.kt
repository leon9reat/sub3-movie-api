package com.medialink.sub3movieapi.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.medialink.sub3movieapi.Const
import kotlin.math.roundToInt

object BindingAdapters {
    @BindingAdapter("poster")
    @JvmStatic
    fun posterImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load("${Const.TMDB_PHOTO_URL}${imageUrl}")
            .override(120, 160)
            .into(view)
    }

    @BindingAdapter("hideIt")
    @JvmStatic
    fun hideIt(view: View, state: Boolean) {
        view.visibility = if (state) View.VISIBLE else View.GONE
    }

    @BindingAdapter("vote")
    @JvmStatic
    fun vote(view: ProgressBar, voteAverage: Float) {
        view.progress = (voteAverage * 10).roundToInt()
    }
}