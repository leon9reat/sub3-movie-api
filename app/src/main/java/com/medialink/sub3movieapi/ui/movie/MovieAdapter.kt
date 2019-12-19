package com.medialink.sub3movieapi.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.medialink.sub2catalogue.models.movie.Movie
import com.medialink.sub3movieapi.R
import com.medialink.sub3movieapi.databinding.MovieItemBinding

class MovieAdapter(
    private var movies: List<Movie>,
    private var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            binding.callback = itemClickListener
            binding.root.setOnClickListener {
                itemClickListener.onItemClicked(item)
            }
        }
    }

    fun update(data: List<Movie>) {
        if (data.isNotEmpty()) {
            movies = data
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val movieItemBinding: MovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item,
            parent,
            false
        )
        return MyViewHolder(movieItemBinding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    interface ItemClickListener {
        fun onItemClicked(movie: Movie)
        fun onLikeClicked(movie: Movie)
        fun onShareClicked(movie: Movie)
    }
}