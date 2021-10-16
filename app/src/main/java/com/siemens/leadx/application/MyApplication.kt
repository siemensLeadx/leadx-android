package com.siemens.leadx.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.siemens.leadx.utils.locale.LocaleHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        LocaleHelper.onAttach(this)
        super.onConfigurationChanged(newConfig)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }
}
