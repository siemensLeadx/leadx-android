package com.siemens.leadx.ui.notifications.container

import android.app.Activity
import com.siemens.leadx.R
import com.siemens.leadx.ui.notifications.NotificationsFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class NotificationsActivity : BaseActivity() {

    companion object {
        fun start(activity: Activity?) {
            activity?.launchActivity<NotificationsActivity>()
        }
    }

    override fun onViewCreated() {
        replaceFragment(
            NotificationsFragment(),
            R.id.fl_container
        )
    }
}
