package com.medialink.sub3movieapi.ui.movie


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.medialink.sub3movieapi.R
import com.medialink.sub3movieapi.databinding.FragmentMovieDetailBinding

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie_detail, container, false
        )

        val args = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).movie
        binding.movie = args

        Toast.makeText(context, "${args.title}", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> Log.d("debug", "home click")
        }
        return super.onOptionsItemSelected(item)
    }
}
