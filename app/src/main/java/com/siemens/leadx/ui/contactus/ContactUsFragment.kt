package com.siemens.leadx.ui.contactus

import com.siemens.leadx.R
import com.siemens.leadx.databinding.FragmentContactUsBinding
import com.siemens.leadx.utils.Constants.CONTACT_US_EMAIL
import com.siemens.leadx.utils.Constants.CONTACT_US_PHONE
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.base.BaseViewModel
import com.siemens.leadx.utils.extensions.makeCall
import com.siemens.leadx.utils.extensions.sendMail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment :
    BaseFragment<FragmentContactUsBinding>(FragmentContactUsBinding::inflate) {

    override fun getCurrentViewModel(): BaseViewModel? = null

    override fun onViewReady() {
        initToolbar()
        initViews()
        initClickListeners()
    }

    private fun initToolbar() {
        with(binding.lToolbar) {
            tvTitle.text = getString(R.string.contact_us)
            ivBack.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            lCustomerService.tvPhone.text = CONTACT_US_PHONE
        }
    }

    private fun initClickListeners() {
        with(binding) {
            lEmail.cvEmail.setOnClickListener {
                CONTACT_US_EMAIL.sendMail(context)
            }
            lCustomerService.cvCustomerService.setOnClickListener {
                CONTACT_US_PHONE.makeCall(context)
            }
        }
    }
}
