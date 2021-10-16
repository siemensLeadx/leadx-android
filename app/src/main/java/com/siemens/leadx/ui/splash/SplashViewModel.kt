package com.siemens.leadx.ui.splash

import com.siemens.leadx.ui.splash.navigator.SplashNavigator
import com.siemens.leadx.utils.SingleLiveEvent
import com.siemens.leadx.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val splashRepository: SplashRepository) :
    BaseViewModel(splashRepository) {

    val navigate = SingleLiveEvent<SplashNavigator>()

    fun startSplashTimer() {
        addSubscription(setTimer())
    }

    private fun setTimer(): Disposable {
        return Completable.timer(
            splashRepository.getSplashTime(),
            TimeUnit.MILLISECONDS,
            AndroidSchedulers.mainThread()
        )
            .subscribe {
                if (isUserLogin(false))
                    navigate.postValue(SplashNavigator.Home)
                else
                    navigate.postValue(SplashNavigator.Authentication)
            }
    }

}
