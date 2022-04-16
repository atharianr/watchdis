package com.atharianr.moviecatalogueapi.ui.home.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.moviecatalogueapi.R
import com.atharianr.moviecatalogueapi.databinding.FragmentTvShowBinding
import com.atharianr.moviecatalogueapi.utils.ViewModelFactory
import com.atharianr.moviecatalogueapi.vo.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding as FragmentTvShowBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val tvShowViewModel: TvShowViewModel by viewModels {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val tvShowAdapter = TvShowAdapter()

            tvShowViewModel.getTvShowPopular().observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.LOADING -> {
                            isLoading(true)
                        }

                        Status.SUCCESS -> {
                            isLoading(false)
                            showEmptyLayout(false)
                            if (it.data != null) {
                                tvShowAdapter.setData(it.data)
                            }
                        }

                        Status.ERROR -> {
                            isLoading(false)
                            showEmptyLayout(true)
                            Toast.makeText(
                                context,
                                getString(R.string.error_info),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            with(binding.rvTvShow) {
                this.layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
                this.adapter = tvShowAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
                rvTvShow.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvTvShow.visibility = View.VISIBLE
            }
        }
    }

    private fun showEmptyLayout(state: Boolean) {
        if (state) {
            binding.rvTvShow.visibility = View.GONE
            binding.tvError.visibility = View.VISIBLE
        } else {
            binding.rvTvShow.visibility = View.VISIBLE
            binding.tvError.visibility = View.GONE
        }
    }
}