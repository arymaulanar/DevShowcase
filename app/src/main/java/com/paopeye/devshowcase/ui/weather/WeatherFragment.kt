package com.paopeye.devshowcase.ui.weather

import android.Manifest
import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import com.paopeye.domain.model.CityAutoComplete
import com.paopeye.domain.model.Weather
import com.paopeye.kit.extension.emptyString
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {
    private val viewModel: WeatherViewModel by viewModel()
    override fun isUseToolbar() = false
    override fun isUseLeftImageToolbar() = false
    private val weathersAdapter = WeathersAdapter()
    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) showPermission()
        }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherBinding {
        return FragmentWeatherBinding.inflate(inflater, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        binding.searchEdit.detachTextWatcher()
    }

    override fun setupView() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        showPermission()
        setupSearchBar()
        setupScrollView()
        setupRecyclerView()
        setupFocusClearing()
        viewModel.onEvent(WeatherViewModel.Event.OnCreate)
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                WeatherViewModel.State.HideLoading -> hideLoading()
                WeatherViewModel.State.ShowLoading -> showLoading()
                is WeatherViewModel.State.ShowWeathers -> showWeathers(it.weathers)
                is WeatherViewModel.State.ShowLoadingSearchBar -> showLoadingSearchBar(it.isLoading)
                is WeatherViewModel.State.ShowCityAutoCompletes -> showCityAutoCompletes(it.cities)
                WeatherViewModel.State.ShowEmptyWeathers -> showEmptyWeathers()
            }
        }
    }

    private fun showCityAutoCompletes(cities: List<CityAutoComplete>) {
        binding.searchEdit.setAutoCompleteData(cities)
    }

    private fun showLoadingSearchBar(isLoading: Boolean) {
        binding.searchEdit.setLoading(isLoading)
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

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
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
        binding.searchEdit.setOnTextChangedListener {
            viewModel.onEvent(WeatherViewModel.Event.OnQuerySearchBar(it))
        }
        binding.searchEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.weatherRecyclerView.visibility = View.GONE
                binding.emptyWeatherText.visibility = View.GONE
                return@setOnFocusChangeListener
            }
            if (weathersAdapter.weathers.isEmpty()) return@setOnFocusChangeListener showEmptyWeathers()
            binding.weatherRecyclerView.visibility = View.VISIBLE
            binding.emptyWeatherText.visibility = View.GONE
        }
        binding.searchEdit.setOnClickAutoComplete {

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupFocusClearing() {
        val root = binding.contentLayout // or use binding.root if appropriate
        root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val focusedView = requireActivity().currentFocus
                if (focusedView is EditText) {
                    focusedView.clearFocus()
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
                }
            }
            false
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
