package com.siemens.leadx.ui.splash

import com.siemens.leadx.utils.base.BaseRepository
import javax.inject.Inject

class SplashRepository @Inject constructor() : BaseRepository() {

    private val splashTimeInMilliSec = 2000L

    fun getSplashTime(): Long {
        return splashTimeInMilliSec
    }
}
