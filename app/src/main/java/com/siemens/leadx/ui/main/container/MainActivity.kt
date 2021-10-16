package com.siemens.leadx.ui.main.container

import android.app.Activity
import com.siemens.leadx.R
import com.siemens.leadx.ui.main.MainFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class MainActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
            finish: Boolean = true,
        ) {
            activity?.launchActivity<MainActivity>()

            if (finish)
                activity?.finishAffinity()
        }
    }

    override fun onViewCreated() {
        replaceFragment(
            MainFragment(),
            R.id.fl_container
        )
    }
}
