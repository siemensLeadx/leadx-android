package com.siemens.leadx.ui.splash.container

import android.annotation.SuppressLint
import android.view.WindowManager
import com.scottyab.rootbeer.RootBeer
import com.siemens.leadx.BuildConfig
import com.siemens.leadx.R
import com.siemens.leadx.ui.splash.SplashFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.replaceFragment
import com.siemens.leadx.utils.extensions.showRootedDialog

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onViewCreated() {
        val rootBeer = RootBeer(this)
        if (rootBeer.isRooted && !BuildConfig.DEBUG)
            showRootedDialog()
        else
            initView()

    }

    private fun initView() {
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
