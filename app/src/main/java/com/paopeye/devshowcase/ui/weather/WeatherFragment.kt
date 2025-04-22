package com.paopeye.devshowcase.ui.weather

import androidx.fragment.app.Fragment
import com.paopeye.devshowcase.R

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }
}
