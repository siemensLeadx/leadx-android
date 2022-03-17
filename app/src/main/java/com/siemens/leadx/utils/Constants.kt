package com.siemens.leadx.utils

import com.siemens.leadx.utils.locale.LocaleLanguage
import java.util.*

object Constants {
    const val SHARED_PREF_NAME = "shared_pref"
    const val FIRST_PAGE = 1
    const val PAGE_SIZE = 20
    const val CONTACT_US_PHONE = "+966567647005"
    const val CONTACT_US_EMAIL = "eyas.khalaf@siemens-healthineers.com "

    // notification
    const val LEAD_ID = "lead_id"

    fun getPrivacyPolicyUrl() =
        if (Locale.getDefault().language == Locale(LocaleLanguage.Arabic.getId()).language)
            "https://siemenshealthineers-leadx.com/static/media/LeadX_Privacy_PolicyAr.a6a2d458.pdf"
        else
            "https://siemenshealthineers-leadx.com/static/media/LeadX_Privacy_PolicyEn.3b9a3827.pdf"

    fun getTermsConditionsUrl() =
        if (Locale.getDefault().language == Locale(LocaleLanguage.Arabic.getId()).language)
            "https://siemenshealthineers-leadx.com/static/media/LeadX_Terms_ConditionsAr.6905ca3f.pdf"
        else
            "https://siemenshealthineers-leadx.com/static/media/LeadX_Terms_ConditionsEn.ce52a2c9.pdf"
}
