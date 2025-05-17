package com.paopeye.devshowcase.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun setupView() {

    }

    override fun isUseToolbar() = false
    override fun isUseLeftImageToolbar() = false

    override fun subscribeState() {

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }
}
