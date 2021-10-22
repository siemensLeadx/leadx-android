package com.siemens.leadx.ui.createlead

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.CreateLookUps
import com.siemens.leadx.data.local.entities.Device
import com.siemens.leadx.data.local.entities.FieldError
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.databinding.FragmentCreateLeadBinding
import com.siemens.leadx.ui.createlead.adapters.DevicesAdapter
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.createLookups
import com.siemens.leadx.utils.extensions.observe
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
        observe(viewModel.fieldError) {
            handleFieldError(it)
        }
        observe(viewModel.createLeadStatus) {
            when (it) {
                is Status.Loading -> showDialogLoading()
                is Status.Success<BaseResponse<Any>> ->
                    handleCreateLeadSuccess(it.data?.message)
                is Status.Error ->
                    onError(it) {
                        hideDialogLoading()
                    }
            }
        }
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
            btnSubmit.setOnClickListener {
                viewModel.createLead(
                    etLeadName.text.toString(),
                    etHospitalName.text.toString(),
                    etRegion.text.toString(),
                    etContactPerson.text.toString(),
                    etAdditionalComment.text.toString(),
                    devicesAdapter?.validateErrors()
                )
            }
        }
    }

    private fun handleFieldError(error: FieldError) {
        with(binding) {
            when (error) {
                is FieldError.NameError -> setErrorField(tilLeadName, error.msg)
                is FieldError.HospitalNameError -> setErrorField(tilHospitalName, error.msg)
                is FieldError.RegionError -> setErrorField(tilRegion, error.msg)
                is FieldError.CustomerStatusError -> setErrorField(tilCustomerStatus, error.msg)
                is FieldError.DateError -> setErrorField(tilDate, error.msg)
                is FieldError.ContactPersonError -> setErrorField(tilContactPerson, error.msg)
                is FieldError.None -> resetErrors()
            }
        }
    }

    private fun resetErrors() {
        with(binding) {
            resetErrorField(tilLeadName)
            resetErrorField(tilHospitalName)
            resetErrorField(tilRegion)
            resetErrorField(tilCustomerStatus)
            resetErrorField(tilDate)
            resetErrorField(tilContactPerson)
        }
    }

    private fun resetErrorField(field: TextInputLayout) {
        field.error = ""
    }

    private fun setErrorField(field: TextInputLayout, msg: String) {
        field.error = msg
    }

    private fun handleCreateLeadSuccess(msg: String?) {
        hideDialogLoading()
        showMsg(msg)
        binding.btnSubmit.isEnabled = false
        activity?.setResult(Activity.RESULT_OK)
        Handler(Looper.getMainLooper()).postDelayed({
            activity?.finish()
        }, 2000)
    }

    companion object {
        fun getInstance(bundle: Bundle?) = CreateLeadFragment().also {
            it.arguments = bundle
        }
    }
}
