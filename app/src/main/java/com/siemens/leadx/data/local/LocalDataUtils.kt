package com.siemens.leadx.data.local

import android.content.Context
import android.provider.Settings
import com.siemens.leadx.data.local.sharedprefs.SharedPrefsUtils
import com.siemens.leadx.utils.locale.LocaleHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalDataUtils @Inject constructor(@ApplicationContext private var context: Context) {

    init {
        context = LocaleHelper.onAttach(context)
    }

    val sharedPrefsUtils = SharedPrefsUtils.getInstance(context)

    @Inject
    lateinit var connectivityUtils: ConnectivityUtils

    fun isNetworkConnected() = connectivityUtils.isConnectedToNetwork()

    fun getString(id: Int): String {
        return context.getString(id)
    }

    fun setLanguage(language: String) {
        sharedPrefsUtils.setLanguage(language)
    }

    fun getLanguage() = sharedPrefsUtils.getLanguage()

    fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}
