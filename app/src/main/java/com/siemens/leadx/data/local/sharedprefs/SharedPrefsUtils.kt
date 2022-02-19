package com.siemens.leadx.data.local.sharedprefs

import android.content.Context
import com.siemens.leadx.data.local.entities.FireBaseToken
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
        // register firebase token with new language
        getFireBaseToken()?.let {
            setFireBaseToken(FireBaseToken(true, it.token))
        }
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

    fun getFireBaseToken(): FireBaseToken? {
        return sharedPrefs.getObject<FireBaseToken>(FIREBASE_TOKEN, FireBaseToken::class.java)
    }

    fun getDidSeeOnBoarding(): Boolean {
        return sharedPrefs.getBoolean(DID_SEE_ON_BOARDING, false)
    }

    fun setDidSeeOnBoarding(didSeeOnBoarding: Boolean) {
        sharedPrefs.putBoolean(DID_SEE_ON_BOARDING, didSeeOnBoarding)
    }

    fun setFireBaseToken(fireBaseToken: FireBaseToken) {
        sharedPrefs.putObject(FIREBASE_TOKEN, fireBaseToken)
    }

    fun clearUserData() {
        saveUser(null)
        // need to register token for new logged in user
        getFireBaseToken()?.let {
            setFireBaseToken(FireBaseToken(true, it.token))
        }
    }

    companion object {
        const val LANGUAGE = "language"
        const val USER = "user"
        const val FIREBASE_TOKEN = "firebase-token"
        const val DID_SEE_ON_BOARDING = "did-see-on-boarding"

        private var sharedPrefsUtils: SharedPrefsUtils? = null
        fun getInstance(context: Context): SharedPrefsUtils {
            return sharedPrefsUtils ?: SharedPrefsUtils(SharedPrefs(context.getSharedPref())).also {
                sharedPrefsUtils = it
            }
        }
    }
}
