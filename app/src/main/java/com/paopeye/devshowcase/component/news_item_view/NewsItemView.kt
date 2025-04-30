package com.paopeye.devshowcase.component.news_item_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.paopeye.devshowcase.databinding.NewsItemViewBinding
import com.paopeye.devshowcase.util.loadImageWithUrl
import com.paopeye.domain.model.Article

class NewsItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: NewsItemViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = NewsItemViewBinding.inflate(inflater, this, true)
    }

    fun setupFromArticle(article: Article) {
        setAuthor(article.getSourceDomain())
        setTitle(article.title)
        setDate(article.getPublishDateFormatted())
        if (article.images.isEmpty()) return
        setImage(article.images.first())
    }

    fun setAuthor(author: String) {
        binding.authorText.text = author
    }

    fun setTitle(title: String) {
        binding.titleText.text = title
    }

    fun setDate(date: String) {
        binding.dateText.text = date
    }

    fun setImage(imageUrl: String) {
        binding.newsImage.loadImageWithUrl(context, imageUrl)
    }
}
