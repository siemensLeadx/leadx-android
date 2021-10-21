package com.siemens.leadx.ui.createlead

import android.view.Gravity
import android.view.Menu
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.siemens.leadx.R
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.LookUp
import com.siemens.leadx.databinding.FragmentCreateLeadBinding
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.observe
import com.siemens.leadx.utils.extensions.showCalender
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateLeadFragment :
    BaseFragment<FragmentCreateLeadBinding>(FragmentCreateLeadBinding::inflate) {

    private val viewModel by viewModels<CreateLeadViewModel>()

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initToolbar()
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

    private fun initObservation() {
        observe(viewModel.businessOpportunitiesStatus) {
            when (it) {
                is Status.Loading -> showDialogLoading()
                is Status.Success<BaseResponse<List<LookUp>>> ->
                    handleBusinessOpportunitiesSuccess(it.data?.data)
                is Status.Error ->
                    onError(it) {
                        hideDialogLoading()
                    }
            }
        }
        observe(viewModel.customerStatus) {
            when (it) {
                is Status.Loading -> showDialogLoading()
                is Status.Success<BaseResponse<List<LookUp>>> ->
                    handleCustomerStatus(it.data?.data)
                is Status.Error ->
                    onError(it) {
                        hideDialogLoading()
                    }
            }
        }
    }

    private fun initViews() {
    }

    private fun initClickListeners() {
        with(binding) {
            etBusinessOpportunityType.setOnClickListener {
                viewModel.getBusinessOpportunities()
            }
            etCustomerStatus.setOnClickListener {
                viewModel.getCustomerStatus()
            }
            etWhenDoesCustomerNeedSystem.setOnClickListener {
                etWhenDoesCustomerNeedSystem.showCalender(requireContext(),
                    viewModel.getCustomerDueDate() ?: 0L) {
                    viewModel.setCustomerDueDate(it)
                }
            }
        }
    }

    private fun handleBusinessOpportunitiesSuccess(list: List<LookUp>?) {
        hideDialogLoading()
        showPopUpMenu(list, binding.etBusinessOpportunityType) {
            viewModel.setBusinessOpportunity(it)
        }
    }

    private fun handleCustomerStatus(list: List<LookUp>?) {
        hideDialogLoading()
        showPopUpMenu(list, binding.etCustomerStatus) {
            viewModel.setCustomerStatus(id)
        }
    }

    private fun showPopUpMenu(
        list: List<LookUp>?,
        view: EditText,
        onClick: (id: Int) -> Unit,
    ) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END)
        list?.forEach {
            popupMenu.menu.add(Menu.NONE, it.id, Menu.NONE, it.name)
        }
        popupMenu.setOnMenuItemClickListener {
            view.setText(it.title)
            onClick.invoke(it.itemId)
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }
}
