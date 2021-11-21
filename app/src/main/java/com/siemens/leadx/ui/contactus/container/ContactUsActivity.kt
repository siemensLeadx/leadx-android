package com.siemens.leadx.ui.contactus.container

import android.app.Activity
import com.siemens.leadx.R
import com.siemens.leadx.ui.contactus.ContactUsFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class ContactUsActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
        ) {
            activity?.launchActivity<ContactUsActivity>()
        }
    }

    override fun onViewCreated() {
        replaceFragment(ContactUsFragment(), R.id.fl_container)
    }
}
