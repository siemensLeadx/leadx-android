package com.siemens.leadx.ui.authentication.login.container

import android.app.Activity
import com.siemens.leadx.R
import com.siemens.leadx.ui.authentication.login.LoginFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class LoginActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
            finish: Boolean = true,
        ) {
            activity?.launchActivity<LoginActivity>()

            if (finish)
                activity?.finishAffinity()
        }
    }

    override fun onViewCreated() {
        replaceFragment(
            LoginFragment(),
            R.id.fl_container
        )
    }
}
