package com.paopeye.devshowcase.ui.profile.credit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paopeye.devshowcase.databinding.ItemCopyrightBinding
import com.paopeye.domain.model.Copyright
import com.paopeye.kit.extension.emptyInt

class ProfileCreditAdapter : RecyclerView.Adapter<ProfileCreditAdapter.ProfileCreditViewHolder>() {
    private var onClickListener: ((String) -> Unit)? = null
    private var types = mutableListOf<Copyright>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileCreditViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCopyrightBinding.inflate(layoutInflater, parent, false)
        return ProfileCreditViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileCreditViewHolder, position: Int) {
        holder.bind(types[position], position) { onClickListener?.invoke(it) }
    }

    override fun getItemCount(): Int = types.size

    fun setOnClickListener(onClick: (String) -> Unit) {
        onClickListener = onClick
    }

    fun updateTypes(copyrights: List<Copyright>) {
        types.clear()
        types.addAll(copyrights)
        notifyItemRangeChanged(emptyInt(), copyrights.size)
    }

    inner class ProfileCreditViewHolder(private val binding: ItemCopyrightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: Copyright,
            position: Int,
            onClick: (String) -> Unit
        ) {
            binding.titleText.text = data.title
            binding.subtitleText.text = data.subtitle
            binding.urlText.text = data.url
            binding.logoImage.setImageResource(data.logo)
            binding.root.setOnClickListener {
                onClick.invoke(data.url)
            }

            if (position == itemCount.dec()) binding.dividerView.visibility = View.GONE
        }
    }
}
