package com.paopeye.devshowcase.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentNewsBinding
import com.paopeye.devshowcase.ui.news_detail.NewsDetailFragment
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.domain.model.Articles
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.extension.emptyString
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment<FragmentNewsBinding>() {
    private val viewModel: NewsViewModel by viewModel()
    override fun isUseToolbar() = true
    override fun isUseLeftImageToolbar() = false
    override fun toolbarTitle() = emptyString()
    private val newsAdapter = NewsAdapter(emptyList())
    private var isLoading = false
    private var totalScrollY = 0
    private var maxScroll = 0

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsBinding {
        return FragmentNewsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            totalScrollY = it.getInt(SCROLL_Y, 0)
            maxScroll = it.getInt(MAX_SCROLL, 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_Y, totalScrollY)
        outState.putInt(MAX_SCROLL, maxScroll)
    }

    override fun setupView() {
        setupRecyclerView()
        viewModel.onEvent(NewsViewModel.Event.OnCreate)
    }

    override fun onPause() {
        binding.newsRecyclerView.smoothScrollToPosition(emptyInt())
        super.onPause()
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is NewsViewModel.State.ShowArticles -> showArticles(it.articles)
                is NewsViewModel.State.ShowLoadingLoadMore -> showLoadingLoadMore(it.isVisible)
                NewsViewModel.State.HideLoading -> {
                    isLoading = false
                    hideLoading()
                }

                NewsViewModel.State.ShowLoading -> {
                    isLoading = true
                    showLoading()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
            setItemViewCacheSize(10)
        }
        newsAdapter.setOnClickListener {
            replaceFragment(NewsDetailFragment.newInstance(it))
        }
        val layoutManager = binding.newsRecyclerView.layoutManager as? LinearLayoutManager
        binding.newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val visibleThreshold = 1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                totalScrollY += dy
                if (maxScroll == 0) {
                    val titleView = layoutManager?.findViewByPosition(emptyInt())
                    maxScroll = titleView?.height ?: 1
                }
                updateToolbarStyling(totalScrollY, maxScroll, getString(R.string.menu_news))
                layoutManager?.let { manager ->
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                    if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + visibleThreshold)) {
                        isLoading = true
                        viewModel.onEvent(NewsViewModel.Event.OnLoadMore)
                    }
                }
            }
        })
    }

    private fun showArticles(articles: Articles) {
        val items = mutableListOf<NewsViewType>()
        if (articles.page <= 1) items.add(NewsViewType.Title(getString(R.string.menu_news)))
        items.addAll(articles.articles.map { NewsViewType.NewsItem(it) })
        newsAdapter.updateInfiniteItems(items)
    }

    private fun showLoadingLoadMore(isVisible: Boolean) {
        isLoading = isVisible
        if (isVisible) {
            newsAdapter.addItem(NewsViewType.Loading)
            return
        }
        newsAdapter.removeItem(NewsViewType.Loading)
    }

    companion object {
        const val TITLE_ID = -1L
        const val SCROLL_Y = "SCROLL_Y"
        const val MAX_SCROLL = "MAX_SCROLL"
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
