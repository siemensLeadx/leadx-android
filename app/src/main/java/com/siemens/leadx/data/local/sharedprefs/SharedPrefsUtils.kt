package com.siemens.leadx.data.local.sharedprefs

import android.content.Context
import com.siemens.leadx.data.remote.entites.UserData
import com.siemens.leadx.utils.extensions.getSharedPref
import com.siemens.leadx.utils.locale.LocaleLanguage

class SharedPrefsUtils private constructor(private val sharedPrefs: SharedPrefs) {

    fun getLanguage(): String {
        return sharedPrefs.getString(LANGUAGE, null)
            ?: LocaleLanguage.getDefaultLanguage()
    }

    fun setLanguage(language: String) {
        sharedPrefs.putString(LANGUAGE, language)
    }

    fun isLoggedInUser(): Boolean {
        return getUser() != null
    }

    fun saveUser(user: UserData?) {
        sharedPrefs.putObject(USER, user)
    }

    fun getUser(): UserData? {
        return sharedPrefs.getObject<UserData>(
            USER,
            UserData::class.java
        )
    }

    fun clearUserData() {
        saveUser(null)
    }

    companion object {
        const val LANGUAGE = "language"
        const val USER = "user"

        private var sharedPrefsUtils: SharedPrefsUtils? = null
        fun getInstance(context: Context): SharedPrefsUtils {
            return sharedPrefsUtils ?: SharedPrefsUtils(SharedPrefs(context.getSharedPref())).also {
                sharedPrefsUtils = it
            }
        }
    }
}
