package com.paopeye.devshowcase.ui.news_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.bundleOf
import androidx.core.view.isVisible
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.component.bottom_sheet_web_view.BottomSheetWebView
import com.paopeye.devshowcase.databinding.FragmentNewsDetailBinding
import com.paopeye.devshowcase.util.applyGradientToTitle
import com.paopeye.devshowcase.util.loadImageWithUrl
import com.paopeye.devshowcase.util.setFullScreenMarginTop
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.domain.model.Article
import com.paopeye.kit.extension.parcelable
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>() {
    private val viewModel: NewsDetailViewModel by viewModel()
    override fun isUseToolbar() = false
    override fun isUseLeftImageToolbar() = false
    override fun toolbarTitle() = article.getPublishDateFormatted()
    override fun isFullScreen() = true
    private lateinit var bottomSheetWebView: BottomSheetWebView

    private val article by lazy {
        arguments?.parcelable(ARTICLE_KEY, Article::class.java) ?: Article()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsDetailBinding {
        return FragmentNewsDetailBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        binding.backButton.setFullScreenMarginTop()
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        bottomSheetWebView = BottomSheetWebView(requireContext())
        viewModel.onEvent(NewsDetailViewModel.Event.OnCreate(article))
        bottomSheetWebView.setOnStateHidden {
            updateButtonState(false)
        }
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is NewsDetailViewModel.State.ShowArticle -> showArticle(it.article)
                is NewsDetailViewModel.State.OpenWebView -> openWebView(it.link, it.isDirect)
            }
        }
    }

    private fun openWebView(link: String, isDirect: Boolean) {
        bottomSheetWebView.showWithUrl(link)
        if (!isDirect) return
        bottomSheetWebView.setOnStateHidden {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showArticle(article: Article) {
        binding.newsImage.visibility = View.VISIBLE
        binding.contentLayout.visibility = View.VISIBLE
        binding.newsImage.loadImageWithUrl(
            requireContext(),
            article.images.firstOrNull(),
            true
        )
        binding.newsTitleText.text = article.title
        binding.newsAuthorText.text = article.getSourceDomain()
        binding.newsDateText.text = article.getPublishDateFormatted()
        binding.newsSummaryText.text = article.summary
        binding.readMoreButton.setOnClickListener {
            updateButtonState(true)
            viewModel.onEvent(NewsDetailViewModel.Event.OnClickReadMore(article.link))
        }
    }

    private fun updateButtonState(isLoading: Boolean) {
        binding.readMoreButton.isVisible = !isLoading
        binding.buttonLoading.isVisible = isLoading
    }

    companion object {
        private const val ARTICLE_KEY = ".articleKey"
        fun newInstance(
            article: Article,
        ): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            fragment.arguments = bundleOf(
                ARTICLE_KEY to article
            )
            return fragment
        }
    }
}
