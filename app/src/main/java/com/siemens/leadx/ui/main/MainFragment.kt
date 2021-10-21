package com.siemens.leadx.ui.main

import androidx.fragment.app.viewModels
import com.siemens.leadx.databinding.FragmentMainBinding
import com.siemens.leadx.ui.createlead.container.CreateLeadActivity
import com.siemens.leadx.ui.profile.container.ProfileActivity
import com.siemens.leadx.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainViewModel>()

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initObservation()
        initViews()
        initClickListeners()
    }

    private fun initObservation() {

    }

    private fun initViews() {
        with(binding) {
            val user = viewModel.getUser()?.user
            lHeader.tvName.text = user?.getNameAsLetters()
        }
    }

    private fun initClickListeners() {
        with(binding) {
            lHeader.tvName.setOnClickListener {
                ProfileActivity.start(activity)
            }
            lCreateLead.cvCreateLead.setOnClickListener {
                CreateLeadActivity.start(activity)
            }
        }
    }
}
