package com.paopeye.devshowcase.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paopeye.devshowcase.databinding.ItemLoadingBinding
import com.paopeye.devshowcase.databinding.ItemNewsBinding
import com.paopeye.devshowcase.databinding.ItemTitleBinding
import com.paopeye.devshowcase.ui.base.BaseMultiViewAdapter
import com.paopeye.devshowcase.ui.news.NewsFragment.Companion.TITLE_ID
import com.paopeye.devshowcase.ui.news_detail.NewsDetailFragment
import com.paopeye.domain.model.Article

class NewsAdapter(items: List<NewsViewType>) : BaseMultiViewAdapter<NewsViewType>(items) {
    private var onClickListener: ((Article) -> Unit) = { }
    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> TitleViewHolder(ItemTitleBinding.inflate(inflater, parent, false))
            1 -> NewsViewHolder(ItemNewsBinding.inflate(inflater, parent, false))
            2 -> LoadingViewHolder(ItemLoadingBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Unsupported viewType: $viewType")
        }
    }

    override fun bindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: NewsViewType,
        position: Int
    ) {
        when (holder) {
            is TitleViewHolder -> holder.bind((item as NewsViewType.Title).title)
            is NewsViewHolder -> holder.bind((item as NewsViewType.NewsItem).article) {
                onClickListener.invoke(it)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return when (val item = items[position]) {
            is NewsViewType.Title -> TITLE_ID
            is NewsViewType.NewsItem -> item.article.title.hashCode().toLong()
            is NewsViewType.Loading -> System.currentTimeMillis() // Unique for loading items
        }
    }

    fun setOnClickListener(callback: (Article) -> Unit) {
        onClickListener = callback
    }

    inner class TitleViewHolder(
        private val binding: ItemTitleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            title: String
        ) {
            binding.titleText.text = title
        }
    }

    inner class LoadingViewHolder(
        binding: ItemLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
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
