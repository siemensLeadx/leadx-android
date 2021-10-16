package com.siemens.leadx.ui.splash.container

import android.annotation.SuppressLint
import android.view.WindowManager
import com.siemens.leadx.R
import com.siemens.leadx.ui.splash.SplashFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.replaceFragment

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onViewCreated() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        replaceFragment(
            SplashFragment(),
            R.id.fl_container
        )
    }
}
