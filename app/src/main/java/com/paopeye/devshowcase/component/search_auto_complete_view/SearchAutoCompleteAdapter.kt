package com.paopeye.devshowcase.component.search_auto_complete_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paopeye.devshowcase.databinding.ItemAutoCompleteBinding
import com.paopeye.domain.model.CityAutoComplete
import com.paopeye.kit.extension.emptyInt

class SearchAutoCompleteAdapter :
    RecyclerView.Adapter<SearchAutoCompleteAdapter.SearchAutoCompleteViewHolder>() {
    private var onClickListener: ((CityAutoComplete) -> Unit)? = null
    var cities = listOf<CityAutoComplete>()
        set(value) {
            field = value
            notifyItemRangeChanged(emptyInt(), value.size)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAutoCompleteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAutoCompleteBinding.inflate(layoutInflater, parent, false)
        return SearchAutoCompleteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAutoCompleteViewHolder, position: Int) {
        holder.bind(cities[position]) { onClickListener?.invoke(it) }
    }

    override fun getItemCount(): Int = cities.size

    fun setOnClickListener(onClick: (CityAutoComplete) -> Unit) {
        onClickListener = onClick
    }

    inner class SearchAutoCompleteViewHolder(private val binding: ItemAutoCompleteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: CityAutoComplete,
            onClick: (CityAutoComplete) -> Unit
        ) {
            binding.titleText.text = data.getFullAddress()
            binding.root.setOnClickListener {
                onClick.invoke(data)
            }
        }
    }
}
