package com.paopeye.devshowcase.ui.news_detail

import android.view.View
import androidx.core.bundle.bundleOf
import androidx.core.content.ContextCompat
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.component.bottom_sheet_web_view.BottomSheetWebView
import com.paopeye.devshowcase.databinding.FragmentNewsDetailBinding
import com.paopeye.devshowcase.util.loadImageWithUrl
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.devshowcase.util.viewBinding
import com.paopeye.domain.model.Article
import com.paopeye.kit.extension.parcelable
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailFragment : BaseFragment() {
    private val viewModel: NewsDetailViewModel by viewModel()
    private val binding by viewBinding(FragmentNewsDetailBinding::bind)
    override fun getLayoutRes() = R.layout.fragment_news_detail
    override fun isUseToolbar() = true
    override fun toolbarTitle() = article.getPublishDateFormatted()
    override fun toolbarLeftClickListener(): (() -> Unit)? = {
        activity?.onBackPressed()
    }

    private val article by lazy {
        arguments?.parcelable(ARTICLE_KEY, Article::class.java) ?: Article()
    }

    override fun setupView(view: View) {
        val color = ContextCompat.getColor(requireContext(), R.color.an_primary)
        updateToolbar(article.getPublishDateFormatted(), true, color)
        viewModel.onEvent(NewsDetailViewModel.Event.OnCreate(article))
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is NewsDetailViewModel.State.ShowArticle -> showArticle(it.article)
            }
        }
    }

    private fun showArticle(article: Article) {
        if (article.images.isNotEmpty()) binding.newsImage.loadImageWithUrl(
            requireContext(),
            article.images.first()
        )
        binding.newsTitleText.text = article.title
        binding.newsSummaryText.text = article.summary
        binding.readMoreButton.setOnClickListener {
            val bottomSheetWebView = BottomSheetWebView(requireContext())
            bottomSheetWebView.showWithUrl(article.link)
        }
    }

    companion object {
        private const val ARTICLE_KEY = ".articleKey"
        fun newInstance(
            article: Article
        ): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            fragment.arguments = bundleOf(
                ARTICLE_KEY to article
            )
            return fragment
        }
    }
}
