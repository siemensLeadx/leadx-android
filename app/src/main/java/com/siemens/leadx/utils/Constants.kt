package com.siemens.leadx.utils

import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.LeadStatus
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

object LeadStatusType {
    const val NEW = 1
    const val VERIFIED = 2
    const val CONFIRMED = 3
    const val APPROVED = 4
    const val REJECTED = 5
    const val PROMOTED = 6
    const val ORDERED = 7

    fun getLeadStatusListWithOutReject(currentStatusId: Int) =
        arrayListOf<LeadStatus>().also {
            it.add(
                LeadStatus(
                    NEW,
                    R.string.new_status
                ).also { leadStatus ->
                    getBackgroundResource(leadStatus, currentStatusId)
                }
            )
            it.add(
                LeadStatus(
                    VERIFIED,
                    R.string.verified
                ).also { leadStatus ->
                    getBackgroundResource(leadStatus, currentStatusId)
                }
            )
            it.add(
                LeadStatus(
                    CONFIRMED,
                    R.string.confirmed
                ).also { leadStatus ->
                    getBackgroundResource(leadStatus, currentStatusId)
                }
            )
            it.add(
                LeadStatus(
                    APPROVED,
                    R.string.approved
                ).also { leadStatus ->
                    getBackgroundResource(leadStatus, currentStatusId)
                }
            )
            it.add(
                LeadStatus(
                    PROMOTED,
                    R.string.promoted
                ).also { leadStatus ->
                    getBackgroundResource(leadStatus, currentStatusId)
                }
            )
            it.add(
                LeadStatus(
                    ORDERED,
                    R.string.booked
                ).also { leadStatus ->
                    getBackgroundResource(leadStatus, currentStatusId)
                }
            )
        }

    private fun getBackgroundResource(leadStatus: LeadStatus, currentStatusId: Int) {
        when {
            leadStatus.id < currentStatusId -> {
                leadStatus.background = R.drawable.bg_circle_gray
                leadStatus.numberColor = R.color.black
                leadStatus.textColor = R.color.black60
            }
            leadStatus.id == currentStatusId -> {
                leadStatus.background = R.drawable.bg_circle_primary_color
                leadStatus.numberColor = R.color.white
                leadStatus.textColor = R.color.black60
            }
            else -> {
                leadStatus.background = R.drawable.bg_circle_gray_stroke_border
                leadStatus.numberColor = R.color.ash_grey
                leadStatus.textColor = R.color.ash_grey
            }
        }
    }
}
