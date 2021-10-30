package com.siemens.leadx.utils

import com.siemens.leadx.utils.locale.LocaleLanguage
import java.util.*

object Constants {
    const val SHARED_PREF_NAME = "shared_pref"
    const val FIRST_PAGE = 1
    const val PAGE_SIZE = 20

    fun getPrivacyPolicyUrl() =
        if (Locale.getDefault().language == Locale(LocaleLanguage.Arabic.getId()).language)
            "https://www.google.com"
        else
            "https://www.google.com"

    fun getTermsConditionsUrl() =
        if (Locale.getDefault().language == Locale(LocaleLanguage.Arabic.getId()).language)
            "https://www.google.com"
        else
            "https://www.google.com"
}
