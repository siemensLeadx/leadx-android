package com.siemens.leadx.ui.onboarding

import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.OnBoardingEntity
import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class OnBoardingRepository @Inject constructor() : BaseRepository() {

    val onBoardingEntityList = ArrayList<OnBoardingEntity>().also {
        it.add(
            OnBoardingEntity(
                R.drawable.img_on_boarding_1,
                R.string.on_boarding_1,
            )
        )
        it.add(
            OnBoardingEntity(
                R.drawable.img_on_boarding_2,
                R.string.on_boarding_2,
            )
        )
        it.add(
            OnBoardingEntity(
                R.drawable.img_on_boarding_3,
                R.string.on_boarding_3,
            )
        )
        it.add(
            OnBoardingEntity(
                R.drawable.img_on_boarding_4,
                R.string.on_boarding_4,
            )
        )
        it.add(
            OnBoardingEntity(
                R.drawable.img_on_boarding_5,
                R.string.on_boarding_5,
            )
        )
    }

    fun setDidSeeOnBoarding() = localDataUtils.sharedPrefsUtils.setDidSeeOnBoarding(true)
}
