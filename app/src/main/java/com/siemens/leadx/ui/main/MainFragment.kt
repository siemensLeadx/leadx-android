package com.siemens.leadx.ui.main

import androidx.fragment.app.viewModels
import com.siemens.leadx.databinding.FragmentMainBinding
import com.siemens.leadx.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainViewModel>()

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
            binding.lHeader.tvName.text = it.getNameAsLetters()
        }
    }

    private fun initClickListeners() {
        binding.lHeader.tvName.setOnClickListener {

        }
    }
}
