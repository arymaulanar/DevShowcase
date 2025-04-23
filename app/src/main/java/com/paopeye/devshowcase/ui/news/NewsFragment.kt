package com.paopeye.devshowcase.ui.news

import android.view.View
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentNewsBinding
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.devshowcase.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment() {
    private val viewModel: NewsViewModel by viewModel()
    private val binding by viewBinding(FragmentNewsBinding::bind)
    override fun getLayoutRes() = R.layout.fragment_news

    override fun setupView(view: View) {
        viewModel.onEvent(NewsViewModel.Event.OnCreate)
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is NewsViewModel.State.ShowArticles -> binding.title.text = it.articles.toString()
            }
        }
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
