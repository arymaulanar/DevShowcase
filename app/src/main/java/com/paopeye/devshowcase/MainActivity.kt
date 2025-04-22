package com.paopeye.devshowcase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.paopeye.domain.model.User
import com.paopeye.devshowcase.databinding.ActivityMainBinding
import com.paopeye.devshowcase.ui.news.NewsFragment
import com.paopeye.devshowcase.ui.profile.ProfileFragment
import com.paopeye.devshowcase.ui.weather.WeatherFragment
import com.paopeye.devshowcase.util.FragmentUtils
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.devshowcase.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupNavigationBar()
        showInitialFragment()
        subscribeState()
        viewModel.onEvent(MainActivityViewModel.Event.OnCreate)
    }

    private fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                is MainActivityViewModel.State.ShowUserData -> showUserData(it.user)
            }
        }
    }

    private fun setupNavigationBar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.navigationBarColor = ContextCompat.getColor(
            this,
            R.color.bottom_navigation
        )
        binding.bottomNav.setOnItemSelectedListener { id ->
            when (id) {
                R.id.menu_news_item -> showFragment(NewsFragment.newInstance())
                R.id.menu_weather_item -> showFragment(WeatherFragment.newInstance())
                R.id.menu_profile_item -> showFragment(ProfileFragment.newInstance())
            }
        }
    }

    private fun showInitialFragment() {
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            showFragment(NewsFragment.newInstance())
            binding.bottomNav.setItemSelected(R.id.menu_news_item)
        }
    }

    private fun showFragment(fragment: Fragment) {
        FragmentUtils.replaceFragment(
            fragmentManager = supportFragmentManager,
            fragment = fragment,
            frameId = R.id.fragment_container
        )
    }

    private fun showUserData(user: User) {
//        binding.userTextView.text = user.userId
    }
}
