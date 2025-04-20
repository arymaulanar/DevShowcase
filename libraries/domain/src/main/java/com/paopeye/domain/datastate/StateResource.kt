package com.paopeye.domain.datastate

data class StateMessage(
    val code: String?,
    val title: String?,
    val message: String?,
    val descriptions: List<String>?,
    val footnote: String?
)
