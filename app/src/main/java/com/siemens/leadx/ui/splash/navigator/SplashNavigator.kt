package com.siemens.leadx.ui.splash.navigator

sealed class SplashNavigator {
    object Home : SplashNavigator()
    object OnBoarding : SplashNavigator()
    object Authentication : SplashNavigator()
}
