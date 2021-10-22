package com.siemens.leadx.ui.profile

import androidx.fragment.app.viewModels
import com.siemens.leadx.R
import com.siemens.leadx.databinding.FragmentProfileBinding
import com.siemens.leadx.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initToolbar()
        initViews()
        initClickListeners()
    }

    private fun initToolbar() {
        with(binding.lToolbar) {
            tvTitle.text = getString(R.string.profile)
            ivBack.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            val user = viewModel.getUser()?.user
            tvName.text = user?.getNameAsLetters()
            tvEmail.text = user?.email
        }
    }

    private fun initClickListeners() {
    }
}
