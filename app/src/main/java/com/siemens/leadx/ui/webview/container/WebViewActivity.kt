package com.siemens.leadx.ui.webview.container

import android.app.Activity
import android.os.Bundle
import com.siemens.leadx.R
import com.siemens.leadx.ui.webview.WebViewFragment
import com.siemens.leadx.utils.base.BaseActivity
import com.siemens.leadx.utils.extensions.launchActivity
import com.siemens.leadx.utils.extensions.replaceFragment
import com.siemens.leadx.utils.extensions.webViewTitle
import com.siemens.leadx.utils.extensions.webViewUrl

class WebViewActivity : BaseActivity() {

    companion object {
        fun start(
            activity: Activity?,
            webViewTitle: String,
            webViewUrl: String,
        ) {
            activity?.launchActivity<WebViewActivity> {
                Bundle().also {
                    it.webViewTitle = webViewTitle
                    it.webViewUrl = webViewUrl
                    this.putExtras(it)
                }
            }
        }
    }

    override fun onViewCreated() {
        replaceFragment(
            WebViewFragment.getInstance(intent?.extras),
            R.id.fl_container
        )
    }
}
