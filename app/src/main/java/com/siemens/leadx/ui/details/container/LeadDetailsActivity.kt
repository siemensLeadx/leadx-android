package com.siemens.leadx.ui.details.container

import android.app.Activity
import android.os.Bundle
import com.siemens.leadx.R
import com.siemens.leadx.ui.details.LeadDetailsFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.id
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class LeadDetailsActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
            id: String,
        ) {
            activity?.launchActivity<LeadDetailsActivity> {
                Bundle().also {
                    it.id = id
                    this.putExtras(it)
                }
            }
        }
    }

    override fun onViewCreated() {
        replaceFragment(LeadDetailsFragment.getInstance(intent.extras), R.id.fl_container)
    }
}