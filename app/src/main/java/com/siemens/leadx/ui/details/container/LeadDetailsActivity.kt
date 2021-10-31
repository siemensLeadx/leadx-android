package com.siemens.leadx.ui.details.container

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.siemens.leadx.R
import com.siemens.leadx.ui.details.LeadDetailsFragment
import com.siemens.leadx.ui.main.container.MainActivity
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.id
import com.siemens.leadx.utils.extensions.launchActivities
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment

class LeadDetailsActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
            id: String,
            startHomeFirst: Boolean = false,
            finish: Boolean = false,
        ) {
            val bundle = Bundle().also {
                it.id = id
            }
            if (!startHomeFirst)
                activity?.launchActivity<LeadDetailsActivity> {
                    this.putExtras(bundle)
                }
            else
                activity?.launchActivities<LeadDetailsActivity>(
                    ArrayList<Intent>().also {
                        it.add(Intent(activity, MainActivity::class.java))
                    }
                ) {
                    this.putExtras(bundle)
                }
            if (finish)
                activity?.finish()
        }
    }

    override fun onViewCreated() {
        replaceFragment(LeadDetailsFragment.getInstance(intent.extras), R.id.fl_container)
    }
}
