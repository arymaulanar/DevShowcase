package com.paopeye.devshowcase

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.paopeye.devshowcase.databinding.ActivityMainBinding
import com.paopeye.devshowcase.ui.news.NewsFragment
import com.paopeye.devshowcase.ui.profile.ProfileFragment
import com.paopeye.devshowcase.ui.weather.WeatherFragment
import com.paopeye.devshowcase.util.FragmentUtils
import com.paopeye.devshowcase.util.viewBinding
import com.paopeye.kit.extension.emptyInt
import java.util.UUID

class MainActivity : AppCompatActivity(), ToolbarListener {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val newsFragment by lazy { NewsFragment.newInstance() }
    private val weatherFragment by lazy { WeatherFragment.newInstance() }
    private val profileFragment by lazy { ProfileFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupNavigationBar()
        showInitialFragment()
        onBackPressedDispatcher.addCallback(
            this
        ) {
            val backstackEntryCount = supportFragmentManager.backStackEntryCount
            if (backstackEntryCount == emptyInt()) {
                finish()
                return@addCallback
            }
            supportFragmentManager.popBackStack()
            if (backstackEntryCount == 1) {
                binding.bottomNav.visibility = VISIBLE
                binding.customToolbarView.leftImageVisibility(false)
            }

        }
    }

    override fun setVisibilityToolbar(isVisible: Boolean) {
        binding.customToolbarView.isVisible = isVisible
    }

    override fun setTitleToolbar(title: String) {
        binding.customToolbarView.title = title
    }

    override fun setVisibilityLeftImageButton(isVisible: Boolean) {
        binding.customToolbarView.leftImageVisibility(isVisible)
    }

    override fun setResourceLeftImageButton(@DrawableRes resource: Int?) {
        if (resource == null) return
        binding.customToolbarView.setResourceLeftImage(resource)
    }

    override fun setListenerLeftImageButton(action: () -> Unit) {
        binding.customToolbarView.setClickListenerLeftImage(action)
    }

    override fun setBackgroundStatusBar(backgroundColor: Int?) {
        if (backgroundColor == null) return
        window.statusBarColor = backgroundColor
    }

    override fun setBackgroundToolbar(backgroundColor: Int?) {
        if (backgroundColor == null) return
        binding.customToolbarView.changeBackgroundColor(backgroundColor)
    }

    fun loadingVisibility(isVisible: Boolean) {
        binding.loadingLayout.isVisible = isVisible
    }

    fun replaceFragment(fragment: Fragment) {
        binding.bottomNav.visibility = GONE
        FragmentUtils.replaceFragment(
            fragmentManager = supportFragmentManager,
            fragment = fragment,
            frameId = R.id.fragment_container
        )
    }

    fun showFragment(fragment: Fragment) {
        FragmentUtils.addFragment(
            fragmentManager = supportFragmentManager,
            fragment = fragment,
            frameId = R.id.fragment_container,
            tag = UUID.randomUUID().toString()
        )
    }

    private fun setupNavigationBar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.bottomNav.setOnItemSelectedListener { id ->
            when (id) {
                R.id.menu_news_item -> loadFragment(newsFragment, NewsFragment::class.java.name)
                R.id.menu_weather_item -> loadFragment(
                    weatherFragment,
                    WeatherFragment::class.java.name
                )

                R.id.menu_profile_item -> loadFragment(
                    profileFragment,
                    ProfileFragment::class.java.name
                )
            }
        }
    }

    private fun loadFragment(fragment: Fragment, tag: String) = with(binding) {
        binding.fragmentContainer.isVisible = false
        bottomNav.isClickable = false
        val transaction = supportFragmentManager.beginTransaction()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        val nextFragment = supportFragmentManager.findFragmentByTag(tag)
        currentFragment?.let { transaction.detach(it) }
        nextFragment?.let { transaction.attach(it) }
        if (nextFragment == null) transaction.add(R.id.fragment_container, fragment, tag)
        transaction.commit()
        binding.fragmentContainer.isVisible = true
        bottomNav.isClickable = true
    }

    private fun showInitialFragment() {
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            binding.bottomNav.setItemSelected(R.id.menu_news_item)
        }
    }

}

interface ToolbarListener {
    fun setVisibilityToolbar(isVisible: Boolean)
    fun setTitleToolbar(title: String)
    fun setVisibilityLeftImageButton(isVisible: Boolean)
    fun setResourceLeftImageButton(@DrawableRes resource: Int?)
    fun setListenerLeftImageButton(action: () -> Unit)
    fun setBackgroundToolbar(backgroundColor: Int?)
    fun setBackgroundStatusBar(backgroundColor: Int?)
}
