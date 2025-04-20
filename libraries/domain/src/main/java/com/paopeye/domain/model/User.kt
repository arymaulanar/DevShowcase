package com.paopeye.domain.model

import com.paopeye.kit.extension.emptyString

data class User(
    val userId: String = emptyString(),
    val fullName: String = emptyString()
)
