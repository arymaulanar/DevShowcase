package com.paopeye.kit.util

import com.paopeye.kit.BuildConfig

object Environment {
    const val IS_PRODUCTION = BuildConfig.ENVIRONMENT == "prod"
    const val IS_DEVELOP = BuildConfig.ENVIRONMENT == "dev"
}
