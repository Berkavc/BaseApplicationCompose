package com.sipay.baseapplicationcompose.ui.moviedetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import com.sipay.baseapplicationcompose.IMAGE_URL
import com.sipay.baseapplicationcompose.R
import com.sipay.baseapplicationcompose.base.BaseFragment
import com.sipay.baseapplicationcompose.databinding.FragmentMovieDetailBinding
import com.sipay.baseapplicationcompose.utils.clickWithThrottle
import com.sipay.baseapplicationcompose.utils.convertDateFormatToLong
import com.sipay.baseapplicationcompose.utils.convertLongToDateFormat
import com.sipay.baseapplicationcompose.utils.glideWithDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailViewModel, FragmentMovieDetailBinding>() {
    override val viewModel: MovieDetailViewModel by viewModels()
    override var layoutRes: Int = R.layout.fragment_movie_detail

    override fun observeViewModel() {}

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {}

    override fun arrangeUI() {
        context?.let { ctx->
            binding.imageViewMovieDetailBack.clickWithThrottle {
                navigateToUpFragment()
            }
            viewModel.result?.let {
                binding.textViewMovieDetailTitle.text = it.title
                glideWithDrawable(
                    context = ctx,
                    url = IMAGE_URL + it.poster_path,
                    imageView = binding.imageViewMovieDetailIcon,
                    placeholder = ContextCompat.getDrawable(
                        ctx,
                        R.drawable.ic_movie
                    ),
                    errorPlaceHolder = ContextCompat.getDrawable(
                        ctx,
                        R.drawable.ic_movie
                    )
                )
                if(!it.overview.isNullOrEmpty()){
                    binding.textViewMovieDetailOverview.text = ctx.resources.getString(R.string.movie_item_over_view, it.overview)
                }else{
                    binding.textViewMovieDetailOverview.text = ctx.resources.getString(R.string.movie_item_over_view_empty)
                }

                it.release_date?.let { date ->
                    val releaseDate = convertLongToDateFormat(convertDateFormatToLong(date))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        binding.textViewMovieDetailDate.text = Html.fromHtml(
                            ctx.resources.getString(R.string.movie_item_date, releaseDate),
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        )
                    } else {
                        binding.textViewMovieDetailDate.text =
                            Html.fromHtml(
                                ctx.resources.getString(
                                    R.string.movie_item_date,
                                    releaseDate
                                )
                            )
                    }
                }
                binding.textViewMovieDetailRating.text = ctx.resources.getString(R.string.movie_detail_rating , it.vote_average.toString())
            }
        }
    }

    override fun gatherArgs() {
        viewModel.result = MovieDetailFragmentArgs.fromBundle(requireArguments()).result
    }

}