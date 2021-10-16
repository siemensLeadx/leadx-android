package com.siemens.leadx.data.local

import android.content.Context
import com.siemens.leadx.data.local.sharedprefs.SharedPrefsUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalDataUtils @Inject constructor(@ApplicationContext private val context: Context) {

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
}
