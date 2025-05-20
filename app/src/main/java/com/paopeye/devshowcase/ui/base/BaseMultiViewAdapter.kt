package com.paopeye.devshowcase.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paopeye.kit.extension.emptyIndex
import com.paopeye.kit.extension.emptyInt

interface ViewTyped {
    val viewType: Int
}

abstract class BaseMultiViewAdapter<T : ViewTyped>(
    initialItems: List<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items = mutableListOf<T>().apply { addAll(initialItems) }
    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int): Int = items[position].viewType
    abstract fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun bindViewHolder(holder: RecyclerView.ViewHolder, item: T, position: Int)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        setupViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        bindViewHolder(holder, items[position], position)

    fun updateItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyItemRangeChanged(emptyInt(), newItems.size.dec())
    }

    fun updateInfiniteItems(newItems: List<T>) {
        val oldPosition = itemCount
        items.addAll(newItems)
        notifyItemRangeInserted(oldPosition, newItems.size)
    }

    fun addItem(newItems: T) {
        items.add(newItems)
        notifyItemInserted(itemCount)
    }

    fun updateItem(item: T) {
        val index = items.indexOf(item)
        if (index == emptyIndex()) return
        items.add(index, item)
        notifyItemInserted(itemCount)
    }

    fun removeItem(item: T) {
        val index = items.indexOf(item)
        if (index == emptyIndex()) return
        items.remove(item)
        notifyItemRemoved(index)
    }
}
