package com.siemens.leadx.ui.profile

import androidx.fragment.app.viewModels
import com.siemens.leadx.databinding.FragmentProfileBinding
import com.siemens.leadx.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initObservation()
        initUser()
        initClickListeners()
    }

    private fun initObservation() {

    }

    private fun initUser() {
        viewModel.getUser()?.user?.let {
            with(binding) {
                tvName.text = it.getNameAsLetters()
                tvEmail.text = it.email
            }
        }
    }

    private fun initClickListeners() {
        with(binding) {
            lToolbar.ivBack.setOnClickListener {
                activity?.finish()
            }
        }
    }
}
