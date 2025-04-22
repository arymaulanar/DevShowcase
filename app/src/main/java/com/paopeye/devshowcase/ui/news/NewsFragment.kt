package com.paopeye.devshowcase.ui.news

import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentNewsBinding
import com.paopeye.devshowcase.util.viewBinding

class NewsFragment : BaseFragment() {

    private val binding by viewBinding(FragmentNewsBinding::bind)

    override fun subscribeState() {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
