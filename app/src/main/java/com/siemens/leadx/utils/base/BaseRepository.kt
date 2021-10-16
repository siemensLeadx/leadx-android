package com.siemens.leadx.utils.base

import com.siemens.leadx.data.local.LocalDataUtils
import com.siemens.leadx.data.remote.entites.UserData
import javax.inject.Inject

/**
 * Created by Norhan Elsawi on 4/10/2021.
 */
abstract class BaseRepository {

    @Inject
    lateinit var localDataUtils: LocalDataUtils

    fun isNetworkConnected() = localDataUtils.isNetworkConnected()

    fun getString(id: Int) = localDataUtils.getString(id)

    fun getCurrentLanguage() = localDataUtils.getLanguage()

    fun setLanguage(language: String) = localDataUtils.setLanguage(language)

    fun isUserLogin() = localDataUtils.sharedPrefsUtils.isLoggedInUser()

    fun saveUser(user: UserData?) =
        localDataUtils.sharedPrefsUtils.saveUser(user)

    fun getUser() = localDataUtils.sharedPrefsUtils.getUser()

    fun clearUserData() = localDataUtils.sharedPrefsUtils.clearUserData()
}
