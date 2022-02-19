package com.siemens.leadx.ui.onboarding.slider

import android.os.Bundle
import com.siemens.leadx.data.local.entities.OnBoardingEntity
import com.siemens.leadx.databinding.FragmentOnboardingSliderBinding
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.base.BaseViewModel
import com.siemens.leadx.utils.extensions.onBoardingEntity

class OnBoardingSliderFragment :
    BaseFragment<FragmentOnboardingSliderBinding>(FragmentOnboardingSliderBinding::inflate) {
    private lateinit var onBoardingEntity: OnBoardingEntity

    override fun getCurrentViewModel(): BaseViewModel? = null

    override fun onViewReady() {
        initArguments()
        initViews()
    }

    private fun initArguments() {
        arguments?.let {
            onBoardingEntity = it.onBoardingEntity
        }
    }

    private fun initViews() {
        with(binding) {
            ivImage.setImageResource(onBoardingEntity.image)
        }
    }

    companion object {
        fun getInstance(bundle: Bundle) =
            OnBoardingSliderFragment().also {
                it.arguments = bundle
            }
    }
}
