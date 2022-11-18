package com.task.movieapp.ui.movie

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.task.movieapp.R
import com.task.movieapp.common.utils.clickWithThrottle
import com.task.movieapp.common.utils.convertDateFormatToLong
import com.task.movieapp.common.utils.convertLongToDateFormat
import com.task.movieapp.common.utils.glideWithDrawable
import com.task.movieapp.data.IMAGE_URL
import com.task.movieapp.databinding.ItemRecyclerviewMovieBinding
import com.task.movieapp.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MovieRecyclerViewAdapter(
    private val context: Context,
    private var movieList: MutableList<Result?>,
) : RecyclerView.Adapter<MovieRecyclerViewAdapter.MainRecyclerViewHolder>(), Filterable {

    private var movieListSearch: MutableList<Result?> =
        mutableListOf()

    private var controlFilterState = true

    internal var onItemClick: (position: Int, item: Result) -> Unit =
        { _, _ -> }

    internal var onItemClickSendHyperLink: (position: Int, item: Result) -> Unit =
        { _, _ -> }

    internal var onItemClickShare: (position: Int, item: Result) -> Unit =
        { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val binding = ItemRecyclerviewMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecyclerViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        movieList[position]?.let {
            holder.bindItems(
                context,
                it,
                position,
                onItemClick,
                onItemClickSendHyperLink,
                onItemClickShare
            )
        }

    }

    class MainRecyclerViewHolder(private val binding: ItemRecyclerviewMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(
            context: Context,
            item: Result,
            position: Int,
            onItemClick: (Int, Result) -> Unit,
            onItemClickSendHyperLink: (Int, Result) -> Unit,
            onItemClickShare: (Int, Result) -> Unit
        ) {
            binding.textViewMovieTitle.text =
                context.resources.getString(R.string.movie_item_title, item.title)
            item.release_date?.let {
                val releaseDate = convertLongToDateFormat(convertDateFormatToLong(it))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.textViewMovieDate.text = Html.fromHtml(
                        context.resources.getString(R.string.movie_item_date, releaseDate),
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                } else {
                    binding.textViewMovieDate.text =
                        Html.fromHtml(
                            context.resources.getString(
                                R.string.movie_item_date,
                                releaseDate
                            )
                        )
                }
            }
            if (!item.overview.isNullOrEmpty()) {
                binding.textViewMovieOverview.text =
                    context.resources.getString(R.string.movie_item_over_view, item.overview)
            } else {
                binding.textViewMovieOverview.text =
                    context.resources.getString(R.string.movie_item_over_view_empty)
            }

            glideWithDrawable(
                context = context,
                url = IMAGE_URL + item.poster_path,
                imageView = binding.imageViewMovie,
                placeholder = ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_movie
                ),
                errorPlaceHolder = ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_movie
                )
            )


            binding.constraintLayoutViewMovie.clickWithThrottle {
                onItemClick.invoke(position, item)
            }

            binding.imageViewMovieShare.clickWithThrottle {
                onItemClickShare.invoke(position, item)
            }

            binding.imageViewMovieLink.clickWithThrottle {
                onItemClickSendHyperLink.invoke(position, item)
            }
        }
    }


    private fun updateDataSource(
        newDataSource: MutableList<Result?>,
        isSearch: Boolean? = null
    ) {
        isSearch?.let {
            this.movieList = newDataSource
            notifyDataSetChanged()
        } ?: run {
            val position = this.itemCount
            this.movieList.addAll(newDataSource)
            notifyItemRangeInserted((position + newDataSource.size), newDataSource.size)
        }
    }

    fun updateDataSourceWithSearch(
        newDataSource: MutableList<Result?>,
        isSearch: Boolean? = null
    ) {
        isSearch?.let {
            this.movieListSearch = newDataSource
            updateDataSource(newDataSource, isSearch)
        } ?: run {
            this.movieListSearch.addAll(newDataSource)
            updateDataSource(newDataSource)
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList: MutableList<Result?>? = null
            constraint?.let {
                if (constraint.length < 3) {
                    if (!controlFilterState) {
                        controlFilterState = true
                        val coroutineCallSearch = CoroutineScope(Dispatchers.Main)
                        coroutineCallSearch.launch {
                            withContext(Dispatchers.Main) {
                                updateDataSource(movieListSearch, true)
                            }
                        }
                    }
                } else {
                    filteredList = mutableListOf()
                    filteredList!!.addAll(movieListSearch)
                    controlFilterState = false
                    val filterPattern =
                        constraint.toString().lowercase(Locale.ROOT).trim()
                    movieListSearch.forEach { item ->
                        item?.original_title?.let {
                            if (!it.lowercase(Locale.ROOT).trim()
                                    .contains(filterPattern)
                            ) {
                                filteredList!!.remove(item)
                            }
                        }
                    }
                }
            }
            val result = FilterResults()
            result.values = filteredList
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.values?.let {
                updateDataSource(it as MutableList<Result?>, true)
            }
        }
    }

}
