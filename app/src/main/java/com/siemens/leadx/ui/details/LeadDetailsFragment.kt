package com.siemens.leadx.ui.details

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.siemens.leadx.R
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.Lead
import com.siemens.leadx.databinding.FragmentLeadDetailsBinding
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.id
import com.siemens.leadx.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadDetailsFragment :
    BaseFragment<FragmentLeadDetailsBinding>(FragmentLeadDetailsBinding::inflate) {

    private val viewModel by viewModels<LeadDetailsViewModel>()
    private lateinit var id: String

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
                is Status.Loading -> showDialogLoading()
                is Status.Success<BaseResponse<Lead>> ->
                    handleGetLeadSuccess(it.data?.data)
                is Status.Error ->
                    onError(it) {
                        hideDialogLoading()
                    }
            }
        }
    }

    private fun initViews() {
        with(binding) {
        }
    }

    private fun initClickListeners() {
    }

    private fun handleGetLeadSuccess(lead: Lead?) {
        hideDialogLoading()
    }

    companion object {
        fun getInstance(bundle: Bundle?) = LeadDetailsFragment().also {
            it.arguments = bundle
        }
    }
}
