package com.paopeye.data.entity

data class ResponseEntity<T>(
    val responseCode: String? = null,
    val responseTitle: String? = null,
    val responseDescription: String? = null,
    val responseDescriptions: List<String>? = null,
    val responseFootnote: String? = null,
    val data: T? = null
) {
    companion object {
        const val SUCCESS_RESPONSE_CODE = "00000"
    }
}
