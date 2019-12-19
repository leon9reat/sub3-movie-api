package com.medialink.sub3movieapi.ui.tv_show


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.medialink.sub3movieapi.R
import com.medialink.sub3movieapi.databinding.FragmentTvShowDetailBinding

/**
 * A simple [Fragment] subclass.
 */
class TvShowDetailFragment : Fragment() {

    private lateinit var binding: FragmentTvShowDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tv_show_detail, container, false
        )

        val args = TvShowDetailFragmentArgs.fromBundle(arguments as Bundle).tvShow
        binding.tvShow = args

        Toast.makeText(context, "${args.name}", Toast.LENGTH_SHORT).show()

        return binding.root
    }


}
