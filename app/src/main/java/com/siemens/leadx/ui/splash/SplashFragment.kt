package com.siemens.leadx.ui.splash

import androidx.fragment.app.viewModels
import com.siemens.leadx.databinding.FragmentSplashBinding
import com.siemens.leadx.ui.authentication.login.container.LoginActivity
import com.siemens.leadx.ui.main.container.MainActivity
import com.siemens.leadx.ui.splash.navigator.SplashNavigator
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initObservation()
        startSplash()
    }

    private fun initObservation() {
        observe(viewModel.navigate) {
            when (it) {
                is SplashNavigator.Authentication ->
                    LoginActivity.start(activity)
                is SplashNavigator.Home -> MainActivity.start(activity)
            }
        }
    }

    private fun startSplash() {
        viewModel.startSplashTimer()
    }
}
