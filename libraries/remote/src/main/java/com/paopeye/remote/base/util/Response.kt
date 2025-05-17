package com.paopeye.remote.base.util

import com.paopeye.data.entity.ResponseEntity

fun mapResponseCode(code: Int): String {
    if (code == 200) return ResponseEntity.SUCCESS_RESPONSE_CODE
    return code.toString()
}
