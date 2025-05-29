package com.paopeye.devshowcase.enums

import com.paopeye.devshowcase.R
import com.paopeye.kit.constant.Urls

enum class ApiProviderType {
    API_NINJAS {
        override fun getLogoResource() = R.drawable.ic_api_ninjas
        override fun getTitleResource() = R.string.api_ninjas
        override fun getSubtitleResource() = R.string.api_ninjas_subtitle
        override fun getWebsiteString() = Urls.API_NINJAS
    },
    FINLIGHT {
        override fun getLogoResource() = R.drawable.ic_finlight
        override fun getTitleResource() = R.string.finlight
        override fun getSubtitleResource() = R.string.finlight_subtitle
        override fun getWebsiteString() = Urls.FINLIGHT
    },
    KOOMOT {
        override fun getLogoResource() = R.drawable.ic_photon
        override fun getTitleResource() = R.string.photon_komoot
        override fun getSubtitleResource() = R.string.photon_komoot_subtitle
        override fun getWebsiteString() = Urls.PHOTON_KOOMOT
    };

    abstract fun getLogoResource(): Int
    abstract fun getTitleResource(): Int
    abstract fun getSubtitleResource(): Int
    abstract fun getWebsiteString(): String
}
