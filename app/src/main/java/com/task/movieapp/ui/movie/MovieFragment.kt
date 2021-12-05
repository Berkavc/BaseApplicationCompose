package com.task.movieapp.ui.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.movieapp.R
import com.task.movieapp.common.base.BaseFragment
import com.task.movieapp.domain.model.ResultData
import dagger.hilt.android.AndroidEntryPoint
import com.task.movieapp.databinding.FragmentMovieBinding
import timber.log.Timber
import androidx.appcompat.widget.SearchView
import com.task.movieapp.common.utils.sendToHyperLink

@AndroidEntryPoint
class MovieFragment : BaseFragment<MovieViewModel, FragmentMovieBinding>() {

    override val viewModel: MovieViewModel by viewModels()
    override var layoutRes: Int = R.layout.fragment_movie

    private lateinit var adapterMovieRecyclerView: MovieRecyclerViewAdapter

    override fun observeViewModel() {
        viewModel.movieList.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Success -> {
                    Timber.e("data_success")
                    it.data?.let {
                        if (it.size > 0) {
                            adapterMovieRecyclerView.updateDataSourceWithSearch(it)
                        }
                    }
                }
                is ResultData.Loading -> {
                    Timber.e("data_loading")
                }
                is ResultData.Failed -> {
                    Timber.e("data_failed")
                }
            }
        })

    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchViewMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterMovieRecyclerView.filter.filter(newText)
                return false
            }

        })
        viewModel.fetchMoviesList()
    }

    override fun arrangeUI() {
        arrangeNotesRecyclerView()
    }

    private fun arrangeNotesRecyclerView() {
        activity?.let {
            adapterMovieRecyclerView = MovieRecyclerViewAdapter(
                it,
                mutableListOf()
            )
            with(binding.recyclerViewMovies) {
                adapter = adapterMovieRecyclerView
                layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
                adapterMovieRecyclerView.onItemClick = { position, item ->
                    activity?.let {
                        sendToHyperLink(it, item.id.toString())
                    }
                }


            }
        }
    }

    override fun gatherArgs() {}

    override fun initBinding() {
        super.initBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }

}