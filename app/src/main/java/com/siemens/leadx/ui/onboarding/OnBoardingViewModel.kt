package com.siemens.leadx.ui.onboarding

import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val repository: OnBoardingRepository) :
    BaseViewModel(repository) {

    fun getOnBoardingEntityList() = repository.onBoardingEntityList

    fun setDidSeeOnBoarding() = repository.setDidSeeOnBoarding()
}
