package com.paopeye.devshowcase.component.forecast_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.databinding.ForecastViewBinding
import com.paopeye.domain.model.Weather

class ForecastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: ForecastViewBinding
    private var adapter: ForecastAdapter

    init {
        val inflater = LayoutInflater.from(context)
        binding = ForecastViewBinding.inflate(inflater, this, true)
        adapter = ForecastAdapter()
        setupRecyclerView()
        binding.titleText.text = resources.getString(R.string.forecast_title)
    }

    private fun setupRecyclerView() {
        binding.forecastRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.forecastRecycler.adapter = adapter
    }

    fun setupView(weathers: List<Weather>) {
        adapter.weathers = weathers
    }
}
