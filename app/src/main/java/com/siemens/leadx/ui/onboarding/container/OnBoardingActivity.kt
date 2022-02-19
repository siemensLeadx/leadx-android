package com.siemens.leadx.ui.onboarding.container

import android.app.Activity
import com.siemens.leadx.R
import com.siemens.leadx.ui.onboarding.OnBoardingFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class OnBoardingActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
            finish: Boolean = true,
        ) {
            activity?.launchActivity<OnBoardingActivity>()
            if (finish)
                activity?.finishAffinity()
        }
    }

    override fun onViewCreated() {
        replaceFragment(
            OnBoardingFragment(),
            R.id.fl_container
        )
    }
}
