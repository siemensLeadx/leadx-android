package com.siemens.leadx.ui.onboarding.slider

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.siemens.leadx.data.local.entities.OnBoardingEntity
import com.siemens.leadx.utils.extensions.onBoardingEntity

class OnBoardingSliderAdapter(fragment: Fragment, private val list: List<OnBoardingEntity>) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return OnBoardingSliderFragment.getInstance(
            Bundle().also {
                it.onBoardingEntity = list[position]
            }
        )
    }

    override fun getItemCount(): Int = list.count()
}
