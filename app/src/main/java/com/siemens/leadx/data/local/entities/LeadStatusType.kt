package com.siemens.leadx.data.local.entities

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.siemens.leadx.R

object LeadStatusType {
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
                    VERIFIED,
                    R.string.verified
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
            leadStatus.id < currentStatusId ->
                setLeadStatus(
                    R.drawable.bg_circle_gray,
                    R.color.black,
                    R.color.black60, leadStatus
                )
            leadStatus.id == currentStatusId ->
                setLeadStatus(
                    R.drawable.bg_circle_primary_color,
                    R.color.white,
                    R.color.black60, leadStatus
                )
            else ->
                setLeadStatus(
                    R.drawable.bg_circle_gray_stroke_border,
                    R.color.ash_grey,
                    R.color.ash_grey, leadStatus
                )
        }
    }

    private fun setLeadStatus(
        @DrawableRes background: Int,
        @ColorRes numberColor: Int,
        @ColorRes textColor: Int,
        leadStatus: LeadStatus,
    ) {
        leadStatus.background = background
        leadStatus.numberColor = numberColor
        leadStatus.textColor = textColor
    }
}
