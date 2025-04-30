package com.paopeye.devshowcase.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paopeye.devshowcase.databinding.ItemNewsBinding
import com.paopeye.domain.model.Article
import com.paopeye.kit.extension.emptyInt

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var onClickListener: ((Article) -> Unit)? = null
    var articles = listOf<Article>()
        set(value) {
            field = value
            notifyItemRangeChanged(emptyInt(), articles.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position]) { onClickListener?.invoke(it) }
    }

    override fun getItemCount(): Int = articles.size

    fun setOnClickListener(onClick: (Article) -> Unit) {
        onClickListener = onClick
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: Article,
            onClick: (Article) -> Unit
        ) {
            binding.newsItemView.setupFromArticle(data)
            binding.root.setOnClickListener {
                onClick.invoke(data)
            }
        }
    }
}
