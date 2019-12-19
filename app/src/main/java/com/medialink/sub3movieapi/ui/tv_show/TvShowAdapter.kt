package com.medialink.sub3movieapi.ui.tv_show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.medialink.sub2catalogue.models.movie.Movie
import com.medialink.sub2catalogue.models.tv_show.TvShow
import com.medialink.sub3movieapi.R
import com.medialink.sub3movieapi.databinding.TvShowItemBinding

class TvShowAdapter(
    private var tvShow: List<TvShow>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<TvShowAdapter.MyViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(tvShow: TvShow)
        fun onLikeClicked(tvShow: TvShow)
        fun onShareClicked(tvShow: TvShow)
    }

    inner class MyViewHolder(val binding: TvShowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvShow) {
            binding.tvShow = item
            binding.callback = itemClickListener

            binding.root.setOnClickListener {
                itemClickListener.onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val tvShowItemBinding: TvShowItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.tv_show_item,
            parent,
            false
        )

        return MyViewHolder(tvShowItemBinding)
    }

    override fun getItemCount(): Int = tvShow.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(tvShow[position])
    }

    fun update(data: List<TvShow>) {
        if (data.isNotEmpty()) {
            tvShow = data
            notifyDataSetChanged()
        }
    }
}