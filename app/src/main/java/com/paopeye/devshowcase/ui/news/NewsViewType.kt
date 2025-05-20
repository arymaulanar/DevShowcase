package com.paopeye.devshowcase.ui.news

import com.paopeye.devshowcase.ui.base.ViewTyped
import com.paopeye.domain.model.Article

sealed class NewsViewType(override val viewType: Int) : ViewTyped {
    data class Title(val title: String) : NewsViewType(0)
    data class NewsItem(val article: Article) : NewsViewType(1)
    data object Loading : NewsViewType(2)
}
