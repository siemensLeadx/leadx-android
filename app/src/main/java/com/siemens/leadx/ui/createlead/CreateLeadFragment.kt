package com.siemens.leadx.ui.createlead

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.CreateLookUps
import com.siemens.leadx.data.local.entities.Device
import com.siemens.leadx.databinding.FragmentCreateLeadBinding
import com.siemens.leadx.ui.createlead.adapters.DevicesAdapter
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.createLookups
import com.siemens.leadx.utils.extensions.showCalender
import com.siemens.leadx.utils.extensions.showPopUpMenu
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateLeadFragment :
    BaseFragment<FragmentCreateLeadBinding>(FragmentCreateLeadBinding::inflate) {

    private val viewModel by viewModels<CreateLeadViewModel>()
    private var devicesAdapter: DevicesAdapter? = null
    private lateinit var createLeadLookUps: CreateLookUps

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initToolbar()
        initArguments()
        initObservation()
        initViews()
        initClickListeners()
    }

    private fun initToolbar() {
        with(binding.lToolbar) {
            tvTitle.text = getString(R.string.create_new_lead)
            ivBack.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun initArguments() {
        arguments?.let {
            this.createLeadLookUps = it.createLookups
        }
    }

    private fun initObservation() {

    }

    private fun initViews() {
        with(binding) {
            rvDevices.adapter = DevicesAdapter(arrayListOf<Device>().also {
                it.add(Device())
            }, createLeadLookUps.devices) {
                showErrorMsg(getString(R.string.selected_before))
            }
                .also {
                    devicesAdapter = it
                }
        }
    }

    private fun initClickListeners() {
        with(binding) {
            etBusinessOpportunity.setOnClickListener {
                etBusinessOpportunity.showPopUpMenu(createLeadLookUps.businessOpportunities) {
                    etBusinessOpportunity.setText(it.name)
                    viewModel.setBusinessOpportunity(it.id)
                }
            }
            etCustomerStatus.setOnClickListener {
                etCustomerStatus.showPopUpMenu(createLeadLookUps.customerStatus) {
                    etCustomerStatus.setText(it.name)
                    viewModel.setCustomerStatus(it.id)
                }
            }
            etDate.setOnClickListener {
                etDate.showCalender(requireContext(),
                    viewModel.getCustomerDueDate() ?: 0L) {
                    viewModel.setCustomerDueDate(it)
                }
            }
            btnAddSystem.setOnClickListener {
                devicesAdapter?.addDevice()
            }
        }
    }

    companion object {
        fun getInstance(bundle: Bundle?) = CreateLeadFragment().also {
            it.arguments = bundle
        }
    }
}
