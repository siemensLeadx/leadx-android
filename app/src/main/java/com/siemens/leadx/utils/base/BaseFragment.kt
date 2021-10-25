package com.siemens.leadx.utils.base

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.siemens.leadx.R
import com.siemens.leadx.data.remote.ErrorResponse
import com.siemens.leadx.databinding.LayoutAlerterBinding
import com.siemens.leadx.ui.authentication.login.container.LoginActivity
import com.siemens.leadx.utils.Status
import com.siemens.leadx.utils.extensions.hideSoftKeyboard
import com.siemens.leadx.utils.extensions.observe
import com.siemens.leadx.utils.extensions.showSoftKeyboard
import com.tapadoo.alerter.Alerter

/**
 * Created by Norhan Elsawi on 4/10/2021.
 */
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>,
) : Fragment() {

    private var _binding: VB? = null

    // Binding variable to be used for accessing views.
    protected val binding
        get() = requireNotNull(_binding)

    private lateinit var pd: Dialog

    abstract fun getCurrentViewModel(): BaseViewModel?

    abstract fun onViewReady()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createCustomProgressDialog()
        onViewReady()
        initListeners()
    }

    private fun initListeners() {
        observe(getCurrentViewModel()?.showLoginDialog) {
            showLoginDialog()
        }
        observe(getCurrentViewModel()?.showNetworkError) {
            showErrorMsg(getString(R.string.check_internet_connection))
        }
    }

    fun showErrorMsg(msg: String?) {
        if (!msg.isNullOrEmpty())
            initAlerter {
                initAlerterView(
                    msg,
                    R.drawable.ic_cancel,
                    R.color.transparent_red,
                    it
                )
            }
    }

    fun showMsg(msg: String?) {
        if (!msg.isNullOrEmpty())
            initAlerter {
                initAlerterView(
                    msg,
                    R.drawable.ic_checked,
                    R.color.transparent_green,
                    it
                )
            }
    }

    private fun initAlerter(onViewIsReady: (view: View?) -> Unit) {
        Alerter.create(requireActivity(), R.layout.layout_alerter)
            .setBackgroundColorRes(android.R.color.transparent)
            .also { alerter ->
                onViewIsReady.invoke(alerter.getLayoutContainer())
            }
            .setTextAppearance(R.style.AlerterCustomTextAppearance)
            .setDuration(3000)
            .enableSwipeToDismiss()
            .show()
    }

    private fun initAlerterView(msg: String, icon: Int, background: Int, view: View?) {
        view?.let {
            with(LayoutAlerterBinding.bind(it)) {
                tvMsg.text = msg
                llContainer.setBackgroundResource(background)
                ivIcon.setImageResource(icon)
            }
        }
    }

    private fun createCustomProgressDialog() {
        pd = Dialog(requireContext(), R.style.DialogCustomTheme)
        pd.setContentView(R.layout.layout_dialog_progress)
        pd.setCancelable(false)
    }

    fun showDialogLoading() {
        if (!pd.isShowing)
            pd.show()
    }

    fun hideDialogLoading() {
        if (pd.isShowing)
            pd.dismiss()
    }

    fun hideSoftKeyboard() {
        activity.hideSoftKeyboard()
    }

    fun showSoftKeyboard() {
        activity.showSoftKeyboard()
    }

    private fun showLoginDialog() {
        // pass
    }

    fun <E> onError(error: Status.Error<E>, showErrorMsg: Boolean = true, handleError: () -> Unit) {
        handleErrorStatus(error.errorResponse, showErrorMsg, handleError)
    }

    fun <E> onError(
        error: Status.ErrorLoadingMore<E>,
        showErrorMsg: Boolean = true,
        handleError: () -> Unit,
    ) {
        handleErrorStatus(error.errorResponse, showErrorMsg, handleError)
    }

    private fun <E> handleErrorStatus(
        errorResponse: ErrorResponse<E>,
        showErrorMsg: Boolean = true,
        handleError: () -> Unit,
    ) {
        if (errorResponse.code == 426)
            handleForceUpdate()
        else if (showErrorMsg) {
            if (!errorResponse.errors.isNullOrEmpty())
                showErrorMsg(errorResponse.errors[0]["error"])
            else
                showErrorMsg(errorResponse.message)
        }
        handleUnAuthorizedUser(errorResponse.code)
        if (!errorResponse.showOnlyErrorMsg)
            handleError.invoke()
    }

    private fun handleForceUpdate() {
        // pass
    }

    private fun handleUnAuthorizedUser(code: Int) {
        getCurrentViewModel()?.let {
            if (it.isUserLogin(false) && code == 401) {
                it.clearUserData()
                // time for user to read msg
                Handler(Looper.getMainLooper()).postDelayed({
                    LoginActivity.start(activity)
                }, 2000)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Alerter.clearCurrent(activity)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
