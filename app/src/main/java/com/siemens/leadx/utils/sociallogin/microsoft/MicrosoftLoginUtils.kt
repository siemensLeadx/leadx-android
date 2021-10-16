package com.siemens.leadx.utils.sociallogin.microsoft

import android.app.Activity
import com.microsoft.graph.models.extensions.User
import com.microsoft.graph.requests.extensions.GraphServiceClient
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAccount
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.IPublicClientApplication.ISingleAccountApplicationCreatedListener
import com.microsoft.identity.client.ISingleAccountPublicClientApplication
import com.microsoft.identity.client.ISingleAccountPublicClientApplication.CurrentAccountCallback
import com.microsoft.identity.client.ISingleAccountPublicClientApplication.SignOutCallback
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.exception.MsalException
import com.siemens.leadx.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Norhan Elsawi
 */
@Singleton
class MicrosoftLoginUtils @Inject constructor() {

    private val SCOPES = arrayOf("Files.Read")
    private var mSingleAccountApp: ISingleAccountPublicClientApplication? = null
    private val compositeDisposable = CompositeDisposable()

    fun login(
        activity: Activity,
        onSuccess: (user: User) -> Unit,
        onError: (msg: String?) -> Unit,
    ) {
        if (mSingleAccountApp == null)
            init(activity, onSuccess, onError)
        else
            loadAccount(activity, onSuccess, onError)
    }

    private fun init(
        activity: Activity,
        onSuccess: (user: User) -> Unit,
        onError: (msg: String?) -> Unit,
    ) {
        // Creates a PublicClientApplication object with res/raw/auth_config_single_account.json
        PublicClientApplication.createSingleAccountPublicClientApplication(activity,
            R.raw.auth_config_single_account,
            object : ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication) {
                    /**
                     * This test app assumes that the app is only going to support one account.
                     * This requires "account_mode" : "SINGLE" in the config json file.
                     */
                    mSingleAccountApp = application
                    loadAccount(activity, onSuccess, onError)
                }

                override fun onError(exception: MsalException) {
                    handleError(onError, exception.message)
                }
            })
    }


    private fun loadAccount(
        activity: Activity,
        onSuccess: (user: User) -> Unit,
        onError: (msg: String?) -> Unit,
    ) {
        mSingleAccountApp?.getCurrentAccountAsync(object : CurrentAccountCallback {
            override fun onAccountLoaded(activeAccount: IAccount?) {
                // You can use the account data to update your UI or your app database.
                if (activeAccount != null)
                    logout({
                        signIn(activity, onSuccess, onError)
                    }, onError)
                else
                    signIn(activity, onSuccess, onError)

            }

            override fun onAccountChanged(
                priorAccount: IAccount?,
                currentAccount: IAccount?,
            ) {
                //pass
            }

            override fun onError(exception: MsalException) {
                handleError(onError, exception.message)
            }
        })
    }

    private fun signIn(
        activity: Activity,
        onSuccess: (user: User) -> Unit,
        onError: (msg: String?) -> Unit,
    ) {
        mSingleAccountApp?.signIn(activity,
            null,
            SCOPES,
            getAuthInteractiveCallback(onSuccess, onError))
    }

    private fun getAuthInteractiveCallback(
        onSuccess: (user: User) -> Unit,
        onError: (msg: String?) -> Unit,
    ): AuthenticationCallback {
        return object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                callGraphAPI(onSuccess, onError, authenticationResult)
            }

            override fun onError(exception: MsalException) {
                /* Failed to acquireToken */
                handleError(onError, exception.message)
            }

            override fun onCancel() {
                /* User canceled the authentication */
                handleError(onError, "")
            }
        }
    }

    private fun callGraphAPI(
        onSuccess: (user: User) -> Unit,
        onError: (msg: String?) -> Unit,
        authenticationResult: IAuthenticationResult,
    ) {
        Observable.fromCallable {
            val graphClient =
                GraphServiceClient.builder().authenticationProvider { request ->
                    request.addHeader("Authorization",
                        "Bearer ${authenticationResult.accessToken}")
                }.buildClient()
            return@fromCallable graphClient.me()
                .buildRequest()
                .get()
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                onSuccess.invoke(user)
                logout({}, onError)
            }, {
                handleError(onError, it.message)
            }).also {
                compositeDisposable.add(it)
            }
    }

    private fun handleError(onError: (msg: String?) -> Unit, msg: String?) {
        onError.invoke(msg)
        logout({}, {})
    }

    private fun logout(
        onLogoutDone: () -> Unit,
        onError: (msg: String?) -> Unit,
    ) {
        mSingleAccountApp?.signOut(object : SignOutCallback {
            override fun onSignOut() {
                onLogoutDone.invoke()
            }

            override fun onError(exception: MsalException) {
                onError.invoke(exception.message)
            }
        })
    }

    fun clearSubscription() {
        if (compositeDisposable.isDisposed.not()) compositeDisposable.clear()
    }
}