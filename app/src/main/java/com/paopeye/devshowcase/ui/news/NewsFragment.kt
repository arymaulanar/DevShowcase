package com.paopeye.devshowcase.ui.news

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.paopeye.devshowcase.MainActivity
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentNewsBinding
import com.paopeye.devshowcase.ui.news_detail.NewsDetailFragment
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.devshowcase.util.viewBinding
import com.paopeye.domain.model.Article
import com.paopeye.kit.extension.emptyString
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment() {
    private val viewModel: NewsViewModel by viewModel()
    private val binding by viewBinding(FragmentNewsBinding::bind)
    override fun getLayoutRes() = R.layout.fragment_news
    override fun isUseToolbar() = true
    override fun toolbarTitle() = emptyString()

    private val newsAdapter = NewsAdapter()

    override fun setupView(view: View) {
        setupScrollView()
        setupRecyclerView()
        viewModel.onEvent(NewsViewModel.Event.OnCreate)
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is NewsViewModel.State.ShowArticles -> showArticles(it.articles)
                NewsViewModel.State.HideLoading -> hideLoading()
                NewsViewModel.State.ShowLoading -> showLoading()
            }
        }
    }

    private fun setupScrollView() {
        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            updateToolbarStyling(scrollY)
        }
    }

    private fun updateToolbarStyling(scrollY: Int) {
        val maxScroll = binding.newsTitleText.height
        val scrollRatio = (scrollY.toFloat() / maxScroll).coerceIn(0f, 1f)
        val startColor = ContextCompat.getColor(requireContext(), R.color.an_primary_variant)
        val endColor = Color.TRANSPARENT
        var title = emptyString()

        val toolbarColor = ArgbEvaluator().evaluate(
            1 - scrollRatio,
            startColor,
            endColor
        ) as Int
        if (scrollRatio == 1f) title = getString(R.string.menu_news)
        updateToolbar(title, false, toolbarColor)
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
        newsAdapter.setOnClickListener {
            (activity as MainActivity).replaceFragment(NewsDetailFragment.newInstance(it))
        }
    }

    private fun showArticles(articles: List<Article>) {
        newsAdapter.articles = articles
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
