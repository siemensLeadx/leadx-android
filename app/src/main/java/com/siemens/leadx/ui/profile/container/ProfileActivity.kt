package com.siemens.leadx.ui.profile.container

import android.app.Activity
import com.siemens.leadx.R
import com.siemens.leadx.ui.profile.ProfileFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class ProfileActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
        ) {
            activity?.launchActivity<ProfileActivity>()
        }
    }

    override fun onViewCreated() {
        replaceFragment(ProfileFragment(), R.id.fl_container)
    }
}
