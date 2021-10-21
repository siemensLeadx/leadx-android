package com.siemens.leadx.ui.createlead.container

import android.app.Activity
import com.siemens.leadx.R
import com.siemens.leadx.ui.createlead.CreateLeadFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class CreateLeadActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
        ) {
            activity?.launchActivity<CreateLeadActivity>()
        }
    }

    override fun onViewCreated() {
        replaceFragment(CreateLeadFragment(), R.id.fl_container)
    }
}