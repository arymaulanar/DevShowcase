package com.paopeye.remote.model

import com.google.gson.annotations.SerializedName
import com.paopeye.data.entity.ResponseEntity

data class BaseResponse<REMOTE, ENTITY>(
    @SerializedName("response_code")
    val responseCode: String? = null,
    val responseTitle: String? = null,
    val responseDescription: String? = null,
    val responseDescriptions: List<String>? = null,
    val responseFootnote: String? = null,
    val data: REMOTE? = null
) {
    fun toEntityWithData(dataMapper: (REMOTE?) -> ENTITY?): ResponseEntity<ENTITY> {
        return ResponseEntity(
            responseCode = responseCode,
            responseTitle = responseTitle,
            responseDescription = responseDescription,
            responseDescriptions = responseDescriptions,
            responseFootnote = responseFootnote,
            data = dataMapper(data)
        )
    }
}
