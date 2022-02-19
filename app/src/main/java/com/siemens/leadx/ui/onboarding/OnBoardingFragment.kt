package com.siemens.leadx.ui.onboarding

import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.siemens.leadx.databinding.FragmentOnboardingBinding
import com.siemens.leadx.ui.authentication.login.container.LoginActivity
import com.siemens.leadx.ui.onboarding.slider.OnBoardingSliderAdapter
import com.siemens.leadx.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment :
    BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private val viewModel by viewModels<OnBoardingViewModel>()

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initSlider()
        initClickListeners()
    }

    private fun initSlider() {
        val entities = viewModel.getOnBoardingEntityList()
        with(binding) {
            OnBoardingSliderAdapter(
                this@OnBoardingFragment,
                entities
            ).also { adapter ->
                vpOnBoarding.adapter = adapter
                diSlider.setViewPager2(vpOnBoarding)
            }
            vpOnBoarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tvTitle.text = getString(entities[position].title)
                    btnStart.isInvisible = position != entities.lastIndex
                }
            })
        }
    }

    private fun initClickListeners() {
        with(binding) {
            btnStart.setOnClickListener {
                viewModel.setDidSeeOnBoarding()
                LoginActivity.start(activity)
            }
        }
    }
}
