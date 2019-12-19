package com.medialink.sub3movieapi.ui.tv_show

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
import com.medialink.sub2catalogue.models.tv_show.TvShow
import com.medialink.sub3movieapi.R
import com.medialink.sub3movieapi.databinding.FragmentTvShowBinding
import kotlinx.android.synthetic.main.layout_error.*
import java.util.*

class TvShowFragment : Fragment(), TvShowAdapter.ItemClickListener {

    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var adapter: TvShowAdapter
    private lateinit var binding: FragmentTvShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            tvShowViewModel = ViewModelProvider(it, ViewModelProvider.NewInstanceFactory())
                .get(TvShowViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tv_show,
            container,
            false
        )

        setupViewModel()
        setupUi()

        if (!Locale.getDefault().language.equals(tvShowViewModel.lokal)) {
            tvShowViewModel.lokal = Locale.getDefault().language
            tvShowViewModel.loadTvShow(1)
        }

        Toast.makeText(context, "TODO: Refresh", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    private fun setupViewModel() {
        tvShowViewModel.tvShow.observe(viewLifecycleOwner, renderTvShow)
        tvShowViewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        tvShowViewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        tvShowViewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private fun setupUi() {
        binding.rvTvShow.setHasFixedSize(true)
        val i = resources.configuration.orientation
        if (i == Configuration.ORIENTATION_PORTRAIT) {
            binding.rvTvShow.layoutManager = LinearLayoutManager(context)
            binding.rvTvShow.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        } else {
            binding.rvTvShow.layoutManager = GridLayoutManager(context, 2)
            binding.rvTvShow.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
        adapter = TvShowAdapter(
            tvShowViewModel.tvShow.value ?: emptyList(),
            this
        )
        binding.rvTvShow.adapter = adapter
        Log.d("debug", "setup ui")
    }

    private val renderTvShow = Observer<List<TvShow>> {
        binding.layoutError.visibility = View.GONE
        binding.layoutEmpty.visibility = View.GONE
        adapter.update(it)
        Log.d("debug", "data updated ${it.count()}")
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        binding.progressTvShow.visibility = visibility
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

    override fun onItemClicked(tvShow: TvShow) {
        val toTvShowDetailFragment = TvShowFragmentDirections
            .actionNavigationTvShowToTvShowDetailFragment(tvShow)
        findNavController().navigate(toTvShowDetailFragment)
    }

    override fun onLikeClicked(tvShow: TvShow) {
        Log.d("debug", "like: ${tvShow.name}")
    }

    override fun onShareClicked(tvShow: TvShow) {
        Log.d("debug", "share: ${tvShow.name}")
    }
}