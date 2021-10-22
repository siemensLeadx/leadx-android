package com.siemens.leadx.ui.createlead.container

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.siemens.leadx.R
import com.siemens.leadx.data.local.entities.CreateLookUps
import com.siemens.leadx.ui.createlead.CreateLeadFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.createLookups
import com.siemens.leadx.utils.extensions.launchActivityForResult
import com.siemens.leadx.utils.extensions.replaceFragment

class CreateLeadActivity : BaseActivity() {

    companion object {
        fun start(
            fragment: Fragment,
            activityResultLauncher: ActivityResultLauncher<Intent>,
            createLookUps: CreateLookUps,
        ) {
            fragment.launchActivityForResult<CreateLeadActivity>(activityResultLauncher) {
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                Bundle().also {
                    it.createLookups = createLookUps
                    this.putExtras(it)
                }
            }
        }
    }

    override fun onViewCreated() {
        replaceFragment(CreateLeadFragment.getInstance(intent.extras), R.id.fl_container)
    }
}