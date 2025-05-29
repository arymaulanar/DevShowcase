package com.paopeye.devshowcase.ui.profile.credit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentProfileCreditBinding
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.domain.model.Copyright
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCreditFragment : BaseFragment<FragmentProfileCreditBinding>() {
    companion object {
        fun newInstance(): ProfileCreditFragment {
            return ProfileCreditFragment()
        }
    }

    private val viewModel: ProfileCreditViewModel by viewModel()
    override fun isUseToolbar() = false
    override fun isUseLeftImageToolbar() = false
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileCreditBinding {
        return FragmentProfileCreditBinding.inflate(inflater, container, false)
    }

    private val copyrightAdapter = ProfileCreditAdapter()

    override fun setupView() {
        binding.copyrightRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = copyrightAdapter
        }
        copyrightAdapter.setOnClickListener {
            viewModel.onEvent(ProfileCreditViewModel.Event.OnClickCopyright(it))
        }
        viewModel.onEvent(ProfileCreditViewModel.Event.OnCreate)
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is ProfileCreditViewModel.State.ShowCopyrights -> showCopyrights(it.copyrights)
                is ProfileCreditViewModel.State.NavigateToExternalBrowser -> navigateToExternalBrowser(
                    it.url
                )
            }
        }
    }

    private fun showCopyrights(copyrights: List<Copyright>) {
        copyrightAdapter.updateTypes(copyrights.withResolvedResources(requireContext()))
    }

    private fun List<Copyright>.withResolvedResources(context: Context): List<Copyright> {
        return map { it.withResolvedResources(context) }
    }
}
