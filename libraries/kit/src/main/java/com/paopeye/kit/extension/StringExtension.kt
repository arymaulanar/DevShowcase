package com.paopeye.kit.extension

fun getDomainName(url: String): String {
    val pattern =
        """(?:(?:https?://)?(?:www\.)?|(?:www\.)?)([a-zA-Z0-9-]+)\.[a-zA-Z]{2,}""".toRegex()
    val matchResult = pattern.find(url)
    return matchResult?.groupValues?.get(1).orEmpty()
}
