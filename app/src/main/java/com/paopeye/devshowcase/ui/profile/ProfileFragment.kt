package com.paopeye.devshowcase.ui.profile

import android.view.View
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment

class ProfileFragment : BaseFragment() {
    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun setupView(view: View) {

    }

    override fun isUseToolbar() = false
    override fun getLayoutRes() = R.layout.fragment_weather
    override fun subscribeState() {

    }
}
