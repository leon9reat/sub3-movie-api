package com.medialink.sub3movieapi.ui.movie

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medialink.sub2catalogue.models.movie.Movie
import com.medialink.sub3movieapi.R
import com.medialink.sub3movieapi.databinding.FragmentMovieBinding
import kotlinx.android.synthetic.main.layout_error.*
import java.util.*

class MovieFragment : Fragment(), MovieAdapter.ItemClickListener {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var binding: FragmentMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            movieViewModel = ViewModelProvider(it, ViewModelProvider.NewInstanceFactory())
                .get(MovieViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie,
            container,
            false
        )

        setupViewModel()
        setupUi()

        if (!Locale.getDefault().language.equals(movieViewModel.lokal)) {
            movieViewModel.lokal = Locale.getDefault().language
            movieViewModel.loadMovie(1)

        }

        Toast.makeText(context, "TODO: Refresh", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    private fun setupViewModel() {
        movieViewModel.movies.observe(viewLifecycleOwner, renderMovies)
        movieViewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        movieViewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        movieViewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private fun setupUi() {
        binding.rvMovie.setHasFixedSize(true)
        val i = resources.configuration.orientation
        if (i == Configuration.ORIENTATION_PORTRAIT) {
            binding.rvMovie.layoutManager = LinearLayoutManager(context)
            binding.rvMovie.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        } else {
            binding.rvMovie.layoutManager = GridLayoutManager(context, 2)
            binding.rvMovie.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
        adapter = MovieAdapter(
            movieViewModel.movies.value ?: emptyList(),
            this
        )
        binding.rvMovie.adapter = adapter
        Log.d("debug", "setup ui")
    }

    private val renderMovies = Observer<List<Movie>> {
        binding.layoutError.visibility = View.GONE
        binding.layoutEmpty.visibility = View.GONE
        adapter.update(it)
        Log.d("debug", "data updated ${it.count()}")
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        binding.progressMovie.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        binding.layoutEmpty.visibility = View.GONE
        binding.layoutError.visibility = View.VISIBLE
        tv_error.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        binding.layoutEmpty.visibility = visibility
    }

    override fun onItemClicked(movie: Movie) {
        Log.d("debug", "item click: ${movie.title}")

        val toMovieDetailFragment = MovieFragmentDirections
            .actionNavigationMovieToMovieDetailFragment(movie)
        findNavController().navigate(toMovieDetailFragment)
    }

    override fun onLikeClicked(movie: Movie) {
        Log.d("debug", "like: ${movie.title}")
    }

    override fun onShareClicked(movie: Movie) {
        Log.d("debug", "share: ${movie.title}")
    }
}