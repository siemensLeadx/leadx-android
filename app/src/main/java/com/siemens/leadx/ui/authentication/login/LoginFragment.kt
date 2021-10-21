package com.siemens.leadx.ui.authentication.login

import android.view.View
import androidx.fragment.app.viewModels
import com.siemens.leadx.data.remote.BaseResponse
import com.siemens.leadx.data.remote.entites.UserData
import com.siemens.leadx.databinding.FragmentLoginBinding
import com.siemens.leadx.ui.main.container.MainActivity
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.base.BaseFragment
import com.siemens.leadx.utils.extensions.observe
import com.siemens.leadx.utils.sociallogin.microsoft.MicrosoftLoginUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var microsoftLoginUtils: MicrosoftLoginUtils

    override fun getCurrentViewModel() = viewModel

    override fun onViewReady() {
        initObservation()
        initClickListeners()
    }

    private fun initObservation() {
        observe(viewModel.status) {
            when (it) {
                is Status.Loading -> showProgressLoading()
                is Status.Success<BaseResponse<UserData>> -> handleSuccess()
                is Status.Error ->
                    onError(it) {
                        hideProgressLoading()
                    }
            }
        }
    }

    private fun initClickListeners() {
        binding.tvMicrosoftLogin.setOnClickListener {
            if (viewModel.isNetworkConnected()) {
                showProgressLoading()
                microsoftLoginUtils.login(requireActivity(), {
                    hideProgressLoading()
                    viewModel.doLogin(it.userPrincipalName, it.givenName, it.surname, it.id)
                }, {
                    hideProgressLoading()
                    showErrorMsg(it)
                })
            }
        }
    }

    private fun showProgressLoading() {
        binding.pbLoading.visibility = View.VISIBLE
        binding.tvMicrosoftLogin.isEnabled = false
    }

    private fun hideProgressLoading(enableBtn: Boolean = true) {
        binding.pbLoading.visibility = View.GONE
        binding.tvMicrosoftLogin.isEnabled = enableBtn
    }

    private fun handleSuccess() {
        MainActivity.start(activity)
        hideProgressLoading(false)
    }

    override fun onDestroyView() {
        microsoftLoginUtils.clearSubscription()
        super.onDestroyView()
    }
}
