package com.paopeye.devshowcase.ui.weather

import android.Manifest
import android.animation.ArgbEvaluator
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentWeatherBinding
import com.paopeye.devshowcase.util.LocationUtils.checkLocationEnabled
import com.paopeye.devshowcase.util.LocationUtils.showLocationDisabledAlert
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.domain.model.Weather
import com.paopeye.kit.extension.emptyString
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {
    private val viewModel: WeatherViewModel by viewModel()
    override fun isUseToolbar() = false
    override fun isUseLeftImageToolbar() = false
    private val weathersAdapter = WeathersAdapter()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherBinding {
        return FragmentWeatherBinding.inflate(inflater, container, false)
    }

    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) showPermission()
        }

    override fun setupView() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        showPermission()
        setupSearchBar()
        setupScrollView()
        setupRecyclerView()
        viewModel.onEvent(WeatherViewModel.Event.OnCreate)
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                WeatherViewModel.State.HideLoading -> hideLoading()
                WeatherViewModel.State.ShowLoading -> showLoading()
                is WeatherViewModel.State.ShowWeathers -> showWeathers(it.weathers)
                WeatherViewModel.State.ShowEmptyWeathers -> showEmptyWeathers()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        binding.searchEdit.detachTextChangedListener()
    }

    private fun showPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsLauncher.launch(PERMISSIONS)
            return
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionsLauncher.launch(PERMISSIONS)
            return
        }
        val isLocationEnabled = checkLocationEnabled(requireContext())
        if (!isLocationEnabled) return showLocationDisabledAlert(requireContext())

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation ?: return
                viewModel.onEvent(WeatherViewModel.Event.OnGetLocation(location))
                fusedLocationProviderClient.removeLocationUpdates(this)
            }
        }
        val locationRequest =
            LocationRequest.Builder(0L).setPriority(Priority.PRIORITY_HIGH_ACCURACY).build()

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun setupSearchBar() {
        binding.searchEdit.setRootView(binding.contentLayout)
        binding.weatherTitleText.doOnPreDraw {
            binding.searchEdit.setHeightTopSearchBar(it.height)
        }
        binding.searchEdit.setOnTextChangedListener {
            viewModel.onEvent(WeatherViewModel.Event.OnQuerySearchBar(it))
        }
    }

    private fun setupScrollView() {
        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            updateToolbarStyling(scrollY)
        }
    }

    private fun updateToolbarStyling(scrollY: Int) {
        val maxScroll = binding.weatherTitleText.height
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
        updateToolbar(title, false, toolbarColor, toolbarColor)
    }

    private fun setupRecyclerView() {
        binding.weatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weathersAdapter
        }
        weathersAdapter.setOnClickListener {
//            replaceFragment(NewsDetailFragment.newInstance(it))
        }
    }

    private fun showWeathers(weathers: List<Weather>) {
        binding.emptyWeatherText.visibility = View.GONE
        binding.weatherRecyclerView.visibility = View.VISIBLE
        weathersAdapter.weathers = weathers
    }

    private fun showEmptyWeathers() {
        binding.weatherRecyclerView.visibility = View.GONE
        binding.emptyWeatherText.visibility = View.VISIBLE
    }

    companion object {
        var PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }
}
