package com.siemens.leadx.ui.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.LeadStatusType
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Lead
import com.siemens.leadx.databinding.FragmentLeadDetailsBinding
import com.siemens.leadx.ui.details.adapters.DevicesAdapter
import com.siemens.leadx.ui.details.adapters.StatusAdapter
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.getFormattedNumberAccordingToLocal
import com.siemens.leadx.utils.extensions.hideShimmerView
import com.siemens.leadx.utils.extensions.id
import com.siemens.leadx.utils.extensions.observe
import com.siemens.leadx.utils.extensions.showShimmerView
import com.siemens.leadx.utils.extensions.toDate2
import com.siemens.leadx.utils.extensions.toDateWithMonthName2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadDetailsFragment :
    BaseFragment<FragmentLeadDetailsBinding>(FragmentLeadDetailsBinding::inflate) {

    private val viewModel by viewModels<LeadDetailsViewModel>()
    private lateinit var id: String
    private var lead: Lead? = null

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initArguments()
        initToolbar()
        initObservation()
        initViews()
        initClickListeners()
    }

    private fun initArguments() {
        arguments?.let {
            this.id = it.id
            viewModel.getLead(this.id)
        }
    }

    private fun initToolbar() {
        with(binding.lToolbar) {
            tvTitle.text = "${getString(R.string.lead_id)} #$id"
            ivBack.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun initObservation() {
        observe(viewModel.status) {
            when (it) {
                is Status.Loading -> handleLoading()
                is Status.Success<BaseResponse<Lead>> ->
                    handleGetLeadSuccess(it.data?.data)
                is Status.Error ->
                    onError(it) {
                        handleError()
                    }
            }
        }
    }

    private fun handleLoading() {
        with(binding) {
            lShimmer.slLeadDetails.showShimmerView()
            svDetails.visibility = View.GONE
            lRetry.llError.visibility = View.GONE
        }
    }

    private fun handleGetLeadSuccess(lead: Lead?) {
        this.lead = lead
        initViews()
        with(binding) {
            lShimmer.slLeadDetails.hideShimmerView()
            svDetails.visibility = View.VISIBLE
        }
    }

    private fun handleError() {
        with(binding) {
            lShimmer.slLeadDetails.hideShimmerView()
            lRetry.llError.visibility = View.VISIBLE
        }
    }

    private fun initClickListeners() {
        binding.lRetry.llRetryLoading.setOnClickListener {
            viewModel.getLead(id)
        }
    }

    private fun initViews() {
        with(binding) {
            lead?.let {
                val list = it.leadStatusListWithSelectedCurrentStatus
                if (list == null) {
                    rvLeadStatus.visibility = View.GONE
                    showRejected()
                } else {
                    rvLeadStatus.adapter = StatusAdapter(list)
                    rvLeadStatus.layoutManager = GridLayoutManager(requireContext(), list.size)
                    if (arrayOf(
                            LeadStatusType.PROMOTED,
                            LeadStatusType.ORDERED
                        ).any { type -> lead?.leadStatusId == type }
                    ) {
                        lRewarded.rlRewards.visibility = View.VISIBLE
                        lRewarded.tvRewardMsg.text = getString(
                            R.string.you_have_been_rewarded,
                            lead?.reward?.getFormattedNumberAccordingToLocal()
                        )
                    }
                }
                initInfo()
                initDetails()
                initDevices()
            }
        }
    }

    private fun showRejected() {
        with(binding.lRejection) {
            cvRejection.visibility = View.VISIBLE
            tvLeadStatus.text = lead?.leadStatus
            tvRejectReason.text = lead?.leadStatusNote
            try {
                tvLeadStatus.setBackgroundColor(Color.parseColor(lead?.leadStatusBackColor))
                tvLeadStatus.setTextColor(Color.parseColor(lead?.leadStatusTextColor))
            } catch (e: Exception) {
                // PASS
            }
        }
    }

    private fun initInfo() {
        with(binding.lInfo) {
            etLeadName.setText(lead?.leadName)
            etLeadDate.setText(lead?.createdOn?.times(1000)?.toDate2())
            etHospitalName.setText(lead?.hospitalName)
            etRegion.setText(lead?.region)
            etContactPerson.setText(lead?.contactPerson)
        }
    }

    private fun initDetails() {
        with(binding.lDetails) {
            etCustomerStatus.setText(lead?.customerStatus)
            etDate.setText(lead?.customerDueDate?.times(1000)?.toDateWithMonthName2())
            if (lead?.comment.isNullOrBlank())
                tilAdditionalComment.visibility = View.GONE
            else
                etAdditionalComment.setText(lead?.comment)
            if (lead?.businessOpportunityType.isNullOrBlank())
                tilBusinessOpportunity.visibility = View.GONE
            else
                etBusinessOpportunity.setText(lead?.businessOpportunityType)
        }
    }

    private fun initDevices() {
        with(binding.lDevices) {
            rvDevices.adapter = DevicesAdapter(lead?.devices ?: arrayListOf())
        }
    }

    companion object {
        fun getInstance(bundle: Bundle?) = LeadDetailsFragment().also {
            it.arguments = bundle
        }
    }
}
