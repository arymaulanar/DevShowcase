package com.paopeye.devshowcase.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentProfileBinding
import com.paopeye.devshowcase.databinding.TabLayoutViewBinding
import com.paopeye.devshowcase.ui.profile.contact.ProfileContactFragment
import com.paopeye.devshowcase.ui.profile.credit.ProfileCreditFragment
import com.paopeye.devshowcase.util.animateWidth
import com.paopeye.devshowcase.util.isVisibleWithAnim
import com.paopeye.devshowcase.util.setDrawableTintColor

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    private val profileContactFragment by lazy { ProfileContactFragment.newInstance() }
    private val profileCreditFragment by lazy { ProfileCreditFragment.newInstance() }
    private lateinit var adapter: ProfileAdapter
    override fun isUseToolbar() = false
    override fun isUseLeftImageToolbar() = false
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun subscribeState() {}
    override fun setupView() {
        val listOfFragments = listOf(profileContactFragment, profileCreditFragment)
        adapter = ProfileAdapter(
            listOfFragments,
            childFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
        val tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val customTabBinding = TabLayoutViewBinding.inflate(layoutInflater)
            if (position == 0) {
                customTabBinding.tabIconImage.setImageResource(R.drawable.ic_attach_email_24dp)
                customTabBinding.tabTitleText.text = resources.getString(R.string.contact_me)
            }
            if (position == 1) {
                customTabBinding.tabIconImage.setImageResource(R.drawable.ic_copyright_24dp)
                customTabBinding.tabTitleText.text = resources.getString(R.string.copyright)
            }
            tab.customView = customTabBinding.root
        }
        tabMediator.attach()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                updateTabAppearance(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab == null) return
                updateTabAppearance(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
        binding.tabLayout.post {
            binding.tabLayout.getTabAt(1)?.select()
        }
    }

    private fun updateTabAppearance(tab: TabLayout.Tab, isSelected: Boolean) {
        val customView = tab.customView ?: return
        val tabText = customView.findViewById<TextView>(R.id.tab_title_text)
        val tabIcon = customView.findViewById<ImageView>(R.id.tab_icon_image)

        val currentWidth = customView.width
        val targetWidth = if (isSelected) {
            tabText.visibility = View.VISIBLE
            customView.measure(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
            )
            customView.measuredWidth
        } else {
            tabText.visibility = View.GONE
            tabIcon.measure(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
            )
            tabIcon.measuredWidth + customView.paddingStart + customView.paddingEnd
        }
        val color = if (isSelected) {
            ContextCompat.getColor(requireContext(), R.color.white)
        } else {
            ContextCompat.getColor(requireContext(), R.color.search_hint_dark)
        }
        tabText.setTextColor(color)
        tabIcon.setDrawableTintColor(color)
        if (isSelected) {
            tabText.visibility = View.INVISIBLE
            customView.animateWidth(currentWidth, targetWidth, 250) {
                tabText.isVisibleWithAnim(true, 150)
            }
            return
        }
        tabText.isVisibleWithAnim(false, 150) {
            customView.animateWidth(currentWidth, targetWidth, 250)
        }
    }
}
